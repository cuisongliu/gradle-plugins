[![Build Status](https://travis-ci.org/iamthechad/javadoc2dash.svg)](https://travis-ci.org/cuisongliu/gradle-plugins.svg?branch=master)
[![Dependency Status](https://www.versioneye.com/user/projects/5913d942a786140061505272/badge.svg?style=flat-square)](https://www.versioneye.com/user/projects/5913d942a786140061505272)
[![license](https://img.shields.io/badge/gradle-3.3-brightgreen.svg)](https://gradle.org)
[![license](https://img.shields.io/github/license/mashape/apistatus.svg)](https://opensource.org/licenses/mit-license.php)

- [Gradle plugin](#gradle-plugin)
  - [MyBatis Generator Gradle Plugin(MBG)](#mbg)
    - [Add the plugin to you project](#add-mbg-to-project)
    - [Specify settings](#set-mbg-settings)
    - [Run the mbg task](#run-mbg-task)
        - [Example](#example)
 


<h1 id="gradle-plugin">Gradle plugin</h1>
 
This project is some gradle plugin collections.So far there are [mbg](#mbg) plugins,etc.
[简体中文](README_ZH.md)
<h2 id="mbg">MyBatis Generator Gradle Plugin(MBG)</h2>
This plugin is based off of  https://github.com/mybatis/generator. This is a gradle plugin of mybatisGeneratorPlugin(mbg).
This plugin has all the  features  of mybatisGeneratorPlugin(mbg),but the plugin realization of groovy from gradle3.3. 

<h3 id="add-mbg-to-project">Add the plugin to you project</h3>
Build script snippet for use in all Gradle versions:


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
    
Build script snippet for new, incubating, plugin mechanism introduced in Gradle 2.1:

    plugins {
      id "com.cuisongliu.plugin.mybatis-generator" version "1.0"
    }


<h3 id="set-mbg-settings">Specify settings</h3>

    mbg {
      skip = true
      jdbc{
        url ="xxx"
      }
      xml{
        mapperMapper="xxx"
      }
    }
    
If no settings are provided, the plugin tries to use sensible defaults.

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
		<td>Specifies whether the mojo overwrites existing files. Default is false. </td>
		<td><code>true</code></td>
	</tr>
	<tr>
        <td colspan="2"><code>generatorFile</code></td>
        <td><code>String</code></td>
        <td>Location of the configuration file.</td>
        <td><code>generatorConfig.xml</code></td>
    </tr>
    <tr>
        <td colspan="2"><code>sqlScript</code></td>
        <td><code>String</code></td>
        <td>Location of a SQL script file to run before generating code.</td>
        <td><code>null</code></td>
    </tr>
    <tr>
        <td colspan="2"><code>consoleable</code></td>
        <td><code>boolean</code></td>
        <td>Specifies whether  progress messages to the console.</td>
        <td><code>false</code></td>
    </tr>
    <tr>
        <td colspan="2"><code>skip</code></td>
        <td><code>boolean</code></td>
        <td>Specifies whether  progress messages to the console.</td>
        <td><code>false</code></td>
    </tr>
    <tr>
        <td colspan="2"><code>contexts</code></td>
        <td><code>String</code></td>
        <td>Comma delimited list of contexts to generate.</td>
        <td><code>null</code></td>
    </tr>
    <tr>
        <td colspan="2"><code>tableNames</code></td>
        <td><code>String</code></td>
        <td>Comma delimited list of table names to generate.</td>
        <td><code>null</code></td>
    </tr>
	<tr>
		<td rowspan="2">&nbsp;</td>
		<td>&nbsp;</td>
		<td>&nbsp;</td>
		<td>&nbsp;</td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td>&nbsp;</td>
		<td>&nbsp;</td>
		<td>&nbsp;</td>
	</tr>
</tbody></table>

<h3 id="run-mbg-task">Run the mbg task</h3>

<h4 id="example">Example</h4>