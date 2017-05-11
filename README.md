[![Build Status](https://travis-ci.org/iamthechad/javadoc2dash.svg)](https://travis-ci.org/cuisongliu/gradle-plugins.svg?branch=master)
[![Dependency Status](https://www.versioneye.com/user/projects/5913d942a786140061505272/badge.svg?style=flat-square)](https://www.versioneye.com/user/projects/5913d942a786140061505272)
[![license](https://img.shields.io/badge/gradle-3.3-brightgreen.svg)](https://gradle.org)
[![license](https://img.shields.io/github/license/mashape/apistatus.svg)](https://opensource.org/licenses/mit-license.php)

- [Gradle插件](#gradle-plugin)
  - [mybatis生成器gradle插件(MBG)](#mbg)
    - [添加mbg插件到你的项目](#add-mbg-to-project)
    - [参数配置](#set-mbg-settings)
    - [运行mbg任务](#run-mbg-task)
        - [Example](#example)
 


<h1 id="gradle-plugin">Gradle插件</h1>

 这个项目有一些gradle的插件集合.到目前位置有[mbg](#mbg)插件等等.
[English](README_EN.md)
<h2 id="mbg">mybatis生成器gradle插件(MBG)</h2>

这个插件是基于https://github.com/mybatis/generator maven插件做的.这个gradle插件是mybatis生成插件.
这个插件拥有了mybatisGeneratorPlugin(mbg)的全部功能,这个插件实现在gradle3.3上使用groovy实现.


<h3 id="add-mbg-to-project">添加mbg插件到你的项目</h3>
使用在所有Gradle版本的构建脚本片段:


    buildscript {
      repositories {
        maven {
          url "https://plugins.gradle.org/m2/"
        }
      }
      dependencies {
        classpath "com.cuisongliu.plugin:mybatis-generator:1.0"
      }
    }

    apply plugin: "com.cuisongliu.plugin.mybatis-generator"
    
为Gradle 2.1中引入的新的，潜在的插件机制构建脚本代码段
    plugins {
      id "com.cuisongliu.plugin.mybatis-generator" version "1.0"
    }


<h3 id="set-mbg-settings">参数配置</h3>

    mbg {
      overwrite = false
      consoleable = true
      jdbc{
        driver = "com.mysql.jdbc.Driver"
        url    = "jdbc:mysql://[ip]:[port]/[dbname]?useSSL=false"
        username = "username"
        password = "password"
      }
      xml{
          resourcesProject = "src/main/java"
          mapperPlugin = tk.mybatis.mapper.generator.MapperPlugin.class
          mapperMapper = "com.cuisongliu.springboot.core.mapper.MyMapper"
          mapperPackage= "com.cuisongliu.mapper"
          modelPackage = "com.cuisongliu.entity"
          xmlPackage =   "com.cuisongliu.mapper"
      }
    }
    
如果没有设置，插件试图智能使用默认值.

<table>
    <thead>
    <tr>
        <th colspan="2">Setting Name</td>
        <th>Type</td>
       	<th>Description</td>
       	<th>Default</td>
    </tr>
    </thead>
	<tbody>
	<tr>
		<td colspan="2"><code>overwrite</code></td>
		<td><code>boolean</code></td>
		<td>是否覆盖已经生成的xml或者代码 </td>
		<td><code>true</code></td>
	</tr>
	<tr>
        <td colspan="2"><code>generatorFile</code></td>
        <td><code>String</code></td>
        <td>mbg的配置文件位置</td>
        <td><code>generatorConfig.xml</code></td>
    </tr>
    <tr>
        <td colspan="2"><code>sqlScript</code></td>
        <td><code>String</code></td>
        <td>要在生成代码之前运行的 SQL 脚本文件的位置.</td>
        <td><code>null</code></td>
    </tr>
    <tr>
        <td colspan="2"><code>consoleable</code></td>
        <td><code>boolean</code></td>
        <td>如果指定该参数，执行过程会输出到控制台。</td>
        <td><code>false</code></td>
    </tr>
    <tr>
        <td colspan="2"><code>skip</code></td>
        <td><code>boolean</code></td>
        <td>是否跳过生成代码的mbg的任务</td>
        <td><code>false</code></td>
    </tr>
    <tr>
        <td colspan="2"><code>contexts</code></td>
        <td><code>String</code></td>
        <td>如果指定了该参数，逗号隔开的这些context会被执行。</td>
        <td><code>null</code></td>
    </tr>
    <tr>
        <td colspan="2"><code>tableNames</code></td>
        <td><code>String</code></td>
        <td>如果指定了该参数，逗号隔开的这个表会被运行。</td>
        <td><code>null</code></td>
    </tr>
	<tr>
		<td rowspan="4"><code>jdbc</code></td>
		<td><code>driver</code></td>
		<td><code>String</code></td>
		<td>jdbc的驱动类.<em><strong>不能为空</td>
		<td><code>null</code></td>
	</tr>
	<tr>
		<td><code>url</code></td>
		<td><code>String</code></td>
		<td>jdbc的数据库url.<em><strong>不能为空</td>
		<td><code>null</code></td>
	</tr>
	<tr>
        <td><code>username</code></td>
        <td><code>String</code></td>
        <td>jdbc的数据库用户名.<em><strong>不能为空</td>
        <td><code>root</code></td>
    </tr>
    <tr>
        <td><code>password</code></td>
        <td><code>String</code></td>
        <td>jdbc的数据库密码.<em><strong>不能为空</td>
        <td><code>null</code></td>
    </tr>
    <tr>
        <td rowspan="7"><code>xml</code></td>
        <td><code>javaProject</code></td>
        <td><code>String</code></td>
        <td>生成java文件所在的目录.</td>
        <td><code>src/main/java</code></td>
    </tr>
    <tr>
        <td><code>resourcesProject</code></td>
        <td><code>String</code></td>
        <td>生成xml配置文件mapper所在的目录.</td>
        <td><code>src/main/resources</code></td>
    </tr>
    <tr>
        <td><code>mapperPackage</code></td>
        <td><code>String</code></td>
        <td>mapper配置,生成的Mapper(dao)所在的包所在的位置.<em><strong>不能为空</td>
        <td><code>null</code></td>
    </tr>
    <tr>
        <td><code>modelPackage</code></td>
        <td><code>String</code></td>
        <td>model配置,生成的实体类所在的包所在的位置.<em><strong>不能为空</td>
        <td><code>null</code></td>
    </tr>
    <tr>
        <td><code>xmlPackage</code></td>
        <td><code>String</code></td>
        <td>mapper的xml配置,生成的mapper的xml所在的包的位置.<em><strong>不能为空</td>
        <td><code>null</code></td>
    </tr>
    <tr>
        <td><code>mapperPlugin</code></td>
        <td><code>Class<? extends PluginAdapter></code></td>
        <td>插件信息,xml中插件的类名.<em><strong>不能为空</td>
        <td><code>tk.mybatis.mapper.generator.MapperPlugin.class</code></td>
    </tr>
    <tr>
        <td><code>mapperMapper</code></td>
        <td><code>String</code></td>
        <td>mapper配置,生成的Mapper方法的父类.<em><strong>不能为空</td>
        <td><code>tk.mybatis.mapper.common.Mapper</code></td>
    </tr>
</tbody></table>

<h3 id="run-mbg-task">运行mbg任务</h3>

<h4 id="example">Example</h4>