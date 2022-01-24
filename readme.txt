homework里的FMBank是总项目，包含客户端和服务器端的所有代码。
	        FMServer是从FMBank中抽取出来的服务器端，用于在华为云服务器上运行。
	        批量开户.xls文件可用于批量开户功能操作

注意！！！该项目用IDEA设计，JDK环境为jdk11 

在本机测试：
pre：（1）打开FMBank项目，在Main类中更改URL属性，在DBsolver中26行更改数据库连接代码。
1.打开FMBank项目
2.运行Server类开启服务端。
3.运行Main类启动客户端，弹出登录界面（管理员id:320200946111 pw:123456）

在云端测试：
pre：（1）在FMBank项目中的Main类中更改URL属性，（2）在FMServer项目中更改DBsolver中26行更改数据库连接代码。
1.将FMServer上传到云端，在FMServer\out\artifacts\FMBank_jar路径下运行FMBank.jar文件，开启服务端
2.在FMBank中运行Main类启动客户端。




