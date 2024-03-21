## 医学SPD项目介绍

**项目描述**：

​    *该项目旨在为医院药品管理提供方便快捷，基于RFID，通过ISO15693协议，实现药品的自动感应和数据管理，提高药品取用效率，减少人工操作，同时实现药品数据的自动记录和数据库存储。*

​    *项目有众多衍生产品规划，包括智能储药柜，自动售货架等多个产品，目前已经完成了核心的硬件装配和远程系统管理和控制。*

**技术栈：**

*SpringBoot、Mybatis、Mybatis Plus、Quartz、Vue、Redis、MySQL、Nginx、Git、Maven。*



### 一、项目介绍

由于硬件层接口分为wifi和usb两种，这里采用适配器模式进行适配兼容。

```text
|-- pgudie-spd-device						设备中心
|   `-- pguide-spd-wifi-adapter				wifi适配器
|-- pguide-api								api层
|-- pguide-business-providers				业务层
|   `-- pguide-spd-face-system-provider		智能箱项目
|-- pguide-entity							公共实体层
|-- pguide-front-spd						前端项目
|-- pguide-spd-client						硬件客户端
|   |-- pguide-spd-center-cloud				云平台控制
|   `-- pguide-spd-center-core				核心控制
`-- pguide-spd-common						工具模块
    `-- pguide-spd-common-web-result		结果返回工具
```

