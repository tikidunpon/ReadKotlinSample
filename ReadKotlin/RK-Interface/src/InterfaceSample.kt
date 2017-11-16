fun <T> println(message: T) {
    print(Thread.currentThread().stackTrace[2].lineNumber.toString() + "行目 ")
    print(message)
    print("\n")
}

//p144 インターフェイスGreeter
interface Greeter {
    val language: String
    fun sayHello(target: String)
}

class EnglishGreeter : Greeter {
    override val language = "English"
    override fun sayHello(target: String) {
        println("Hello, $target")
    }
}

//p145 複数のインターフェイスの実装
open class Superclass
interface Foo
interface Bar
class MyClass: Superclass(), Foo, Bar

//p146 同一シグネチャのメソッドの実装
interface Foo1 {
    fun execute()
}

interface Bar1 {
    fun execute()
}

class FooBar: Foo1, Bar1 {
    override fun execute() {
        println("FooBar")
    }
}

//p147 インターフェイスとクラスで同一シグネチャメソッド
interface Foo2 {
    fun execute()
}

open class Superclass2 {
    open fun execute() {
        println("Superclass")
    }
}

class FooSubclass: Superclass2(), Foo2

//p147 実装を持った2つのインターフェースの実装
interface Hoge {
    fun execute() {
        println("Hoge")
    }
}

interface Fuga {
    fun execute() {
        println("Fuga")
    }
}

//class HogeFuga: Hoge, Fuga

//p148 インターフェースHogeの実装を使用する
class HogeFuga2: Hoge, Fuga {
    override fun execute() {
        super<Hoge>.execute()
    }
}

//p149 インターフェースの継承
interface Foo3 {
    fun aaa()
    fun bbb()
}

interface Bar3: Foo3 {
    override fun aaa() {}
    fun ccc()
}

class Baz: Bar3 {
    override fun bbb() {}
    override fun ccc() {}
}

//p150 デリゲーション
interface Greeter2 {
    fun sayHello(target: String)
    fun sayHello()
}

open class JapaneseGreeter: Greeter2 {
    override fun sayHello() {
        this.sayHello("匿名")
    }

    override fun sayHello(target: String) {
        println("こんにちは、${target}さん")
    }
}

class JapaneseGreeterWithRecording: JapaneseGreeter() {
    private val _targets: MutableSet<String> = mutableSetOf()

    val targets: Set<String>
        get() = _targets

    override fun sayHello(target: String) {
        _targets += target
        super.sayHello(target)
    }
}

//p152 委譲を行うバージョン
class JapaneseGreeterWithRecording2: Greeter2 {
    private val greeter: Greeter2 = JapaneseGreeter()

    private val _targets: MutableSet<String> = mutableSetOf()

    val targets: Set<String>
        get() = _targets

    override fun sayHello() {
        greeter.sayHello()
    }

    override fun sayHello(target: String) {
        _targets += target
        greeter.sayHello(target)
    }
}

//p153 クラスデリゲーションを使った例
class GreeterWithRecording(private val greeter: Greeter2): Greeter2 by greeter {
    private val _targets: MutableSet<String> = mutableSetOf()

    val targets: Set<String>
        get() = _targets

    override fun sayHello(target: String) {
        _targets += target
        greeter.sayHello(target)
    }
}

fun main(args: Array<String>) {

    val g1 = JapaneseGreeter()
    g1.sayHello()
    g1.sayHello("たろう")

    val g2 = JapaneseGreeterWithRecording()
    g2.sayHello("うらがみ")
    g2.sayHello("がくぞ")
    println(g2.targets)

    g2.sayHello("***")
    g2.sayHello()

    println(g2.targets)

    val g3 = JapaneseGreeterWithRecording2()
    g3.sayHello()
    g3.sayHello("たろう")
    println(g3.targets)

    val g4 = GreeterWithRecording(JapaneseGreeter())
    g4.sayHello()
    g4.sayHello("たなか")
    println(g4.targets)
}