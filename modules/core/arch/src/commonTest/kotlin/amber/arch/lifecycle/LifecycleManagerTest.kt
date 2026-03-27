package amber.arch.lifecycle

import io.kotest.assertions.throwables.shouldThrowExactly
import io.kotest.core.spec.style.FunSpec
import io.kotest.koin.KoinExtension
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject
import kotlin.getValue

class LifecycleManagerTest : KoinTest, FunSpec() {
    private val callLog = mutableListOf<String>()

    init {
        beforeEach { callLog.clear() }

        context("LifecycleManagerWithComponents") {
            val testModule = module {
                lifecycleComponent(named("first")) {
                    RecordingComponent(
                        "first",
                        InitPriority.MAX,
                        callLog
                    )
                }
                lifecycleComponent(named("default")) {
                    RecordingComponent(
                        "default",
                        InitPriority.DEFAULT,
                        callLog
                    )
                }
                lifecycleComponent(named("last")) {
                    RecordingComponent(
                        "last",
                        InitPriority.MIN,
                        callLog
                    )
                }
            }

            extension(KoinExtension(modules = listOf(CoreLifecycleModule, testModule)))

            val manager: LifecycleManager by inject()

            test("init calls all components in descending priority order") {
                manager.init()
                callLog shouldBe listOf("first:init", "default:init", "last:init")
            }

            test("dispose calls all components in ascending priority order") {
                manager.dispose()
                callLog shouldBe listOf("last:dispose", "default:dispose", "first:dispose")
            }
        }
        context("EmptyLifecycleManager") {
            test("works with no components registered") {
                val empty = LifecycleManager(emptyList())
                empty.init()
                empty.dispose()
            }
        }

        context("FailureHandling") {
            test("init continues through failures and throws after all components are called") {
                val error = RuntimeException("first failed")
                val manager = LifecycleManager(
                    listOf(
                        FailingComponent(InitPriority.MAX, failOnInit = error),
                        RecordingComponent("default", InitPriority.DEFAULT, callLog),
                        RecordingComponent("last", InitPriority.MIN, callLog),
                    ),
                )

                val thrown = shouldThrowExactly<RuntimeException> { manager.init() }
                thrown.message shouldBe "first failed"
                callLog shouldBe listOf("default:init", "last:init")
            }

            test("init collects multiple failures as suppressed exceptions") {
                val manager = LifecycleManager(
                    listOf(
                        FailingComponent(InitPriority.MAX, failOnInit = RuntimeException("first failed")),
                        RecordingComponent("default", InitPriority.DEFAULT, callLog),
                        FailingComponent(InitPriority.MIN, failOnInit = RuntimeException("last failed")),
                    ),
                )

                val thrown = shouldThrowExactly<RuntimeException> { manager.init() }
                thrown.message shouldBe "first failed"
                thrown.suppressedExceptions.size shouldBe 1
                thrown.suppressedExceptions[0].shouldBeInstanceOf<RuntimeException>()
                thrown.suppressedExceptions[0].message shouldBe "last failed"
                callLog shouldBe listOf("default:init")
            }

            test("dispose continues through failures and throws after all components are called") {
                val error = RuntimeException("default failed")
                val manager = LifecycleManager(
                    listOf(
                        RecordingComponent("first", InitPriority.MAX, callLog),
                        FailingComponent(InitPriority.DEFAULT, failOnDispose = error),
                        RecordingComponent("last", InitPriority.MIN, callLog),
                    ),
                )

                val thrown = shouldThrowExactly<RuntimeException> { manager.dispose() }
                thrown.message shouldBe "default failed"
                callLog shouldBe listOf("last:dispose", "first:dispose")
            }

            test("dispose collects multiple failures as suppressed exceptions") {
                val manager = LifecycleManager(
                    listOf(
                        FailingComponent(InitPriority.MAX, failOnDispose = RuntimeException("first failed")),
                        RecordingComponent("default", InitPriority.DEFAULT, callLog),
                        FailingComponent(InitPriority.MIN, failOnDispose = RuntimeException("last failed")),
                    ),
                )

                val thrown = shouldThrowExactly<RuntimeException> { manager.dispose() }
                thrown.message shouldBe "last failed"
                thrown.suppressedExceptions.size shouldBe 1
                thrown.suppressedExceptions[0].message shouldBe "first failed"
                callLog shouldBe listOf("default:dispose")
            }

            test("init does not catch errors") {
                val manager = LifecycleManager(
                    listOf(
                        FailingComponent(InitPriority.MAX, failOnInit = OutOfMemoryError("oom")),
                        RecordingComponent("last", InitPriority.MIN, callLog),
                    ),
                )

                val thrown = shouldThrowExactly<OutOfMemoryError> { manager.init() }
                thrown.message shouldBe "oom"
                callLog shouldBe emptyList()
            }

            test("dispose does not catch errors") {
                val manager = LifecycleManager(
                    listOf(
                        FailingComponent(InitPriority.MAX, failOnDispose = OutOfMemoryError("oom")),
                        RecordingComponent("last", InitPriority.MIN, callLog),
                    ),
                )

                val thrown = shouldThrowExactly<OutOfMemoryError> { manager.dispose() }
                thrown.message shouldBe "oom"
                callLog shouldBe listOf("last:dispose")
            }
        }
    }
}
