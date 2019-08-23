---
title: groovy
---
# 数据类型
## 基本类型
Groovy提供多种内置数据类型。以下是在Groovy中定义的数据类型的列表
- byte: 这是用来表示字节值。例如2。
- short: 这是用来表示一个短整型。例如10。
- int: 这是用来表示整数。例如1234。
- long: 这是用来表示一个长整型。例如10000090。
- float: 这是用来表示32位浮点数。例如12.34。
- double: 这是用来表示64位浮点数，这些数字是有时可能需要的更长的十进制数表示。例如12.3456565。
- char: 这定义了单个字符文字。例如“A”。
- Boolean: 这表示一个布尔值，可以是true或false。
- String: 这些是以字符串的形式表示的文本。例如，“Hello World”的。

## 对象类型
对应基本类型的包装类型：
- java.lang.Byte
- java.lang.Short
- java.lang.Integer
- java.lang.Long
- java.lang.Float
- java.lang.Double
- java.math.BigInteger
- java.math.BigDecimal

# 变量
在 groovy 中，除了可以像 Java 中那样定义变量外，还可以使用 `def` 关键字
```groovy
String name = "Groovy";
def name1 = "Groovy";
```

**运算符，循环语句，条件语句 同 Java 类似**

# 方法
Groovy中的方法是使用返回类型或使用 `def` 关键字定义的。方法可以接收任意数量的参数。
定义参数时，不必显式定义类型。可以添加修饰符，如 `public`，`private` 和 `protected` 。默认情况下，如果未提供可见性修饰符，则该方法为 `public`。
除了默认参数外，其他方式同 Java 类似。
```groovy
/**
* 可以设置默认参数，但是默认参数应该在参数列表的末尾定义
*/
def methodName(int parameter1, parameter2, parameter3=1) { 
   return parameter1 + parameter2 + parameter3;
}
println(methodName(1, 2));
```

# 文件 I/O
## 读取文件
方法 `eachLine` 内置在 Groovy 中的 `File` 类中，目的是确保文本文件的每一行都被读取。
```groovy
import java.io.File 
class Example { 
   static void main(String[] args) { 
      new File("E:/Example.txt").eachLine {  
         line -> println "line : $line"; 
      } 
   } 
}
```

## 读取文件的内容到字符串
```groovy
File file = new File("E:/Example.txt") 
println file.text 
```

## 写入文件
```groovy
new File('E:/','Example.txt').withWriter('utf-8') { 
    writer -> writer.writeLine 'Hello World' 
}  
```

## 测试文件是否是目录
```groovy
def file = new File('E:/') 
println "File? ${file.isFile()}" 
println "Directory? ${file.isDirectory()}" 
```

**Groovy 的字符串可以用单引号，双引号和三引号表示，其中三引号字符串可以换行**

# groovy 范围
范围是指定值序列的速记，范围由序列中的第一个和最后一个表示， Range 可以包含或者排除：
- 1..10: 包含范围的示例
- 1 .. <10: 独占范围的示例
- 'a'..'x': 范围也可以由字符组成
- 10..1: 范围也可以按降序排列
- 'x'..'a': 范围也可以由字符组成并按降序排列。

