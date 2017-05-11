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

<h3 id="run-mbg-task">Run the mbg task</h3>

<h4 id="example">Example</h4>