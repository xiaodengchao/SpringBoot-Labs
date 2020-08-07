优秀教程:

[芋道 Spring Boot Elasticsearch 入门](http://www.iocoder.cn/Spring-Boot/Elasticsearch/)

[Elasticsearch RestApi文档-版本7.8](https://www.elastic.co/guide/en/elasticsearch/reference/7.8/rest-apis.html)

[图解 Elasticsearch 原理](http://www.iocoder.cn/Fight/Schematic-Elasticsearch-principle/?self)

[全文搜索引擎选 ElasticSearch 还是 Solr？](http://www.iocoder.cn/Fight/Select-ElasticSearch-or-Solr-as-the-full-text-search-engine/?self)

[别再说你不会 ElasticSearch 调优了，都给你整理好了](http://www.iocoder.cn/Fight/There's-no-ElasticSearch-tuning:-there-you-have-it/?self)

[Elasticsearch 如何做到亿级数据查询毫秒级返回？](http://www.iocoder.cn/Fight/Elasticsearch-how-to-achieve-millionth-level-data-query-millisecond-level-return/?self)

[日均 5 亿查询量的京东订单中心，为什么舍 MySQL 用 ES ?](http://www.iocoder.cn/Fight/Why-use-ES-instead-of-MySQL-for-jd-order-center-with-an-average-daily-query-volume-of-500-million/?self)



**Spring data ElasticSearch版本对应关系**

| **Spring Data Elasticsearch** | **Elasticsearch** | **Spring Boot** |
| ----------------------------- | ----------------- | --------------- |
| 4.0.x                         | 7.6.2             | 2.3.x           |
|                               |                   | 2.2.x           |
|                               |                   | 2.1.x           |
|                               |                   | 2.0.x           |
|                               |                   | 1.5.x           |



**Elastic Search基础知识**

文档(Document): 文档是ES中可以被搜索的最小单元，是ES的基本数据结构，
每个文档都有一个唯一的id，文档会被序列化为JSON结构,保存在ES当中

Document的元数据:
_index: 索引名
_type: 类型,默认为_doc
_id: 唯一id,可以指定也可以由ES自动生成
_source: 文档的原始JSON数据
_all: 文档所有字段内容的整合,ES7.0后已废除
_version: 文档的版本信息
_source: 相关性打分

- 索引(Index): 索引是文档的容器,是一类文档的集合

  - ​	Index体现了逻辑空间上的概念: 每个索引都有自己的Mapping定义，

  ​	用于定义包含的文档的字段名和字段类型

  - ​	Shard体现在物理空间的概念: 索引的数据分散在Shard上

- 索引的Mapping和Settings

  - ​	Mapping定义文档字段的类型

  - ​	Setting定义不同的数据分布    

索引的不同语意

- 名词: 一个Elasticsearch集群中,可以创建很多不同的索引

- 动词: 保存一个文档到Elasticsearch的过程也叫索引(indexing)

  - ES中,创建一个倒排索引的过程

- 名词: 一个B树索引,一个倒排索引

  

Type
  -  6.0之前，一个Index可以建立多个Types
  - 6.0开始，Type只能设置一个并被标记为Deprecated
  - 7.0开始，一个索引只能创建一个Type-“_doc”

传统关系型数据库和Elasticsearch的区别
  - Elasticsearch- Schemaless /相关性/高性能全文检索
  - RDBMS - 事务性 / Join

抽象与类比

| RDBMS  | Elasticsearch |
| ------ | ------------- |
| Table  | Index(Type)   |
| Row    | Document      |
| Column | Filed         |
| Schema | Mapping       |
| SQL    | DSL           |



##### 分布式特性

- Elasticsearch的分布式架构的好处
  - 存储的水平扩容
  - 提高系统的可用性，部分节点停止服务，整个集群的服务不受影响
- Elasticsearch的分布式架构
  - 不同的集群通过不同的名字来区分，默认名字“elasticsearch”
  - 通过配置文件修改，或者在命令行中 -E cluster.name=mycluster来设定
  - 一个集群可以有一个或者多个节点

##### 节点

- 节点是一个Elasticsearch的实例

  - 本质上就是一个JAVA进程

  - 一台机器可以运行多个Elasticsearch进程，但是生产环境一般建议

    一台机器上只运行一个Elasticsearch实例

- 每一个节点都有名字，通过配置文件配置，或者启动时候 -E node.name=node1指定

- 每一个节点在启动之后，会分配一个UID，保存在data目录下



##### Master-eligible nodes和Master Node

- 每个节点启动后，默认就是一个Master eligible节点
  - 可以设置 node.master: false 禁止
- Master-eligible节点可以参加选主流程，成为Master节点
- 当第一个节点启动时候，它会将自己选举成Master节点
- 每个节点上都保存了集群的状态，只有Master节点才能修改集群的状态信息
  - 集群状态(Cluster State)，维护了一个集群中，必要的信息
    - 所有的节点信息
    - 所有的索引和其相关的Mapping与Setting信息
    - 分片的路由信息
  - 任意节点都能修改信息会导致数据的不一致性



##### Data Node & Coordinating Node

- Data Node

  - 可以保存数据的节点，叫做Data Node。负责保存分片数据。在数据扩展上

    起到了至关重要的作用

- Coordinating Node

  - 负责接受Client的请求，将请求分发到合适的节点，最终把结果汇总到一起
  - 每个节点默认都起到了Coordinating Node的职责



##### 其他的节点类型

- Hot & Warm Node（冷热节点，热节点配置高，冷节点配置低）

  - 不同硬件配置的Data Node，用来实现Hot & Warm 架构，降低集群部署成本

- Machine Learning Node

  - 负责跑机器学习的Job，用来做异常检测

- Tribe Node

  - (5.3 开始使用 Cross Cluster Search) Tribe Node连接到不同的Elasticsearch集群，

    并且支持将这些集群当成一个单独的集群处理，在5.3之后的版本废弃



##### 配置节点类型

- 开发环境中一个节点可以承担多种角色
- 生产环境中，应该设置单一的角色的节点（dedicated node）

| 节点类型          | 配置参数    | 默认值                                                    |
| ----------------- | ----------- | --------------------------------------------------------- |
| master eligible   | node.master | true                                                      |
| data              | node.data   | true                                                      |
| ingest            | node.ingest | true                                                      |
| coordinating only | 无          | 每个节点默认都是coordinating节点。设置其他类型全部为false |
| machine learning  | node.ml     | true（需 enable x-pack）                                  |



##### 分片（Primary Shard & Replica Shard）

- 主分片，用以解决数据水平扩展的问题。通过主分片，可以将数据分布到集群内的所有节点之上
  - 一个分片是一个运行的Lucene的实例
  - 主分片数在索引创建时指定，后续不允许修改，除非Reindex
- 副本，用以解决数据高可用的问题。分片是主分片的拷贝
  - 副本分片数，可以动态调整
  - 增加副本数，还可以在一定程度上提高服务的可用性（读取的吞吐）
- 一个三节点的集群中，blogs 索引的分片分布情况
  
  - 思考: 增加一个节点或改大主分片数对系统的影响
  
    答: 主分片数已设定，后续增加节点和调整主分片数也无法实现水平扩展，除非重建索引，如果数据量过大，会非常耗时

![image-20200806155429671](C:\Users\xiaod\AppData\Roaming\Typora\typora-user-images\image-20200806155429671.png)

##### 分片的设定

- 对于生产环境中分片的设定，需要提前做好容量规划
  - 分片数设置过小
    - 导致后续无法增加节点实现水平扩展
    - 单个分片的数据量太大，导致数据重新分配耗时
  - 分片数设置过大，7.0开始，默认主分片由5改成了1，解决了over-sharding的问题
    - 影响搜索结果的相关性打分，影响统计结果的准确性
    - 单个节点上过多的分片，会导致资源浪费，同时也会影响性能

##### 查看集群的健康状况

- Green - 主分片与副本都正常分配
- Yellow - 主分片全部正常分配，有副本未能正常分配
- Red - 有主分片未能分配
  - 例如，当服务器的磁盘容量超过85%时，去创建了一个新索引

![image-20200806160418989](C:\Users\xiaod\AppData\Roaming\Typora\typora-user-images\image-20200806160418989.png)

##### 文档的CRUD

- Type名，约定都用_doc
- Create - 如果ID已经存在，会失败
- Index - 如果ID不存在，创建新的文档。否则，先删除现有的文档，再创建新的文档，版本会增加
- Update - 文档必须已经存在，更新只会对相应字段做增量修改

| Index  | PUT  my_index/_doc/{id}                                      |
| ------ | ------------------------------------------------------------ |
| Create | PUT  my_index/_create/{id}     POST  my_index/_doc（不指定ID，自动生成） |
| Read   | GET  my_index/_doc/{id}                                      |
| Update | POST  my_index/_update/{id}                                  |
| Delete | DELETE  my_index/_doc/{id}                                   |

