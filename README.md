<p align="center">
<a href="http://www.luckybugq.cn" target="_blank">
<img width="200" src="img/onos.png"></a>
</p>
 
<p align="center"><span style="font-size:50px">onosApps - 基于ONOS控制器的SDN网络应用</span>
</p>

- 基于 `ONOS` 开发的SDN网络路由优化 `App`
- 如果觉得不错 给个 `Star` 支持一下 🤓

## 功能介绍
- deviceStatistics：记录SDN网络运行过程中的交换机、主机、链路、流量、出入包等数据并存入MySQL数据库，需要配置数据库地址、用户名和密码。
- predict: 采用lstm（长短期记忆网络）的方法预测实时SDN网络链路流量，需要预训练的lstm机器学习模型文件。
- ml-routing: 根据流量预测结果自适应调整SDN网络中流量转发路径，达到路由优化的效果。
## 使用方法
- 嵌入ONOS代码库
- 修改$ONOS_ROOT/tools/build/bazel/modules.bzl文件
- 在app中添加记录"//apps/appname:onos-apps-appname-oar"(appname即为app文件夹名)
- 通过bazel构建整个ONOS
## 声明
- 适配版本：ONOS 2.1.0-SNAPSHOT ONOS 2.2.0-SNAPSHOT ONOS 2.3.0-SNAPSHOT 测试通过