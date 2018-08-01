# dubbox

dubbox 集成了 zookeeper，可以很容易地用它的自动发现功能等做负载均衡等。

dubbox 是在 Spring 下开发的，所以需要集成 Spring 环境。

# thrift

thrift 远程调用协议支持跨语言，它需要在使用之前先通过 thrift 的工具生成相应的代码。

其基本类型有：

- bool: A boolean value (true or false)
- byte: An 8-bit signed integer
- i16: A 16-bit signed integer
- i32: A 32-bit signed integer
- i64: A 64-bit signed integer
- double: A 64-bit floating point number
- string: A text string encoded using UTF-8 encoding

还支持结构体，也就是 java 中的类。
还支持容器类型，比如 `list`, `map`, `set` 等。

```thrift
# 文件名为 KeyMap.thrift
namespace java rpc.thrift.data
struct KeyMap {
    1:string id,
    2:string key
}
```

```thrift
include "KeyMap.thrift"

namespace java rpc.thrift.service

service HelloService {
    KeyMap.KeyMap service(1:KeyMap.KeyMap queryData)
}
```

运行命令会在相应的路径下生成代码：
```
thrift --gen <language> <Thrift filename>
```