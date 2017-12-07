# sqlUtils
数据库表生成javabean

利用java文件操作，生成对应的javabean文件，目前只适合mysql

修改sqlInf.json相关信息，

  用户名:"userName",
  
  
  密码:"password"
  
  驱动:"driver": "com.mysql.jdbc.Driver"(目前只支持mysql)
  
  路径:"url"
  
  
  表名:"tables": [],(支持多表格一起生成)
  
  
 生成文件的路径目录: "classPath": "/Users/admin/code/java"
 
 
 由于个人习惯使用lombok，所以并没有生成相对应的get，set方法，而是添加对应注解
 可以手动设置
