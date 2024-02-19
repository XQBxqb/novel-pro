项目描述:小说管理系统由小说门户系统、作家后台管理系统、平台后台管理系统等多个子系统构成。包括小 说推荐、作品检索、小说排行榜、小说阅读、小说评论、会员中心、作家专区、充值订阅、新闻发布等功能

技术选型: SpringBoot、MySql、OSS、Elasticsearch、Redis、Nacos、Shiro

技术描述：
•基于JWT、Shiro、Redis实现单点登录功能  
•Mysql使用主从分离模式,提升数据库访问效率    
•Redis使用延迟双删策略,使得主从模式数据库数据一致,灵活使用zset实现排行榜快查询,使用分布式
•保证分布式环境下共享资源同步访问    
•使用Elasticsearch作为搜索引擎,提高检索效率,同时提供可选的Mysql全文索引

## License

This project is licensed under the Apache License 2.0 - see the LICENSE.txt file for details.