package com.cuisongliu.plugin.generator.mybatis.extension
/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017 cuisongliu@qq.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
class MybatisGeneratorExtension {
    boolean overwrite = true
    def generatorFile = "generatorConfig.xml"
    String javaProject = "src/main/java"
    String resourcesProject = "src/main/resources"
    boolean isResourceFile = false
    boolean verbose = false
    String sqlScript = null
    boolean skip=false;
    //如果指定了该参数，逗号隔开的这个表会被运行， 这些表名必须和 <table> 配置中的表面完全一致。只有指定的这些表会被执行。 如果没有指定该参数，所有的表都会被执行。 按如下方式指定表明:
    String contexts=null
    //如果指定了该参数，逗号隔开的这些context会被执行。 这些指定的context必须和配置文件中 <context> 元素的 id 属性一致。 只有指定的这些contextid会被激活执行。如果没有指定该参数，所有的context都会被激活执行。
    String tableNames=null
}


