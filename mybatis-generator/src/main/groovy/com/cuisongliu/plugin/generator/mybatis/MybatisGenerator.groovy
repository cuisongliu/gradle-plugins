package com.cuisongliu.plugin.generator.mybatis

import org.gradle.api.DefaultTask
import org.gradle.api.GradleException
import org.gradle.api.Project
import org.gradle.api.tasks.TaskAction
import org.mybatis.generator.api.MyBatisGenerator
import org.mybatis.generator.config.xml.ConfigurationParser
import org.mybatis.generator.internal.DefaultShellCallback
import org.mybatis.generator.internal.util.StringUtility
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
class MybatisGenerator extends DefaultTask {
    def javaProjectFile
    def resourcesProjectFile
    def xmlParams = [:]
    def generatorFile
    @TaskAction
    void generator() {
        if (project.mbg.skip) {
            println("MyBatis generator is skipped.")
            return;
        }
        //设置generatorConfig.xml的位置
        if (!project.mbg.isResourceFile) {
            generatorFile = project.file("generatorConfig.xml")
        } else {
            generatorFile = project.file("src/main/resources/${project.mbg.generatorFile}")
        }
        javaProjectFile = project.file(project.mbg.javaProject)
        resourcesProjectFile = project.file(project.mbg.resourcesProject)
        //验证
        validation(project)
        for (def param : xmlParams)
           println("环境变量输出:[${param.key}:${param.value}]")
        //sqlScript callback
        def sqlScriptRunner = new SqlScriptRunner(project.mbg.sqlScript,project.mbg.jdbc.url,project.mbg.jdbc.username,project.mbg.jdbc.password)
        sqlScriptRunner.execSqlScript()

        List<String> warnings = new ArrayList<String>();
        //tableNames变量的处理信息
        Set<String> fullyqualifiedTables = new HashSet<String>();
        setTableNamesValues(fullyqualifiedTables, project)
        //contexts变量的处理信息
        Set<String> contextsToRun = new HashSet<String>();
        setContextsValues(contextsToRun, project)
        try {
            def extra = new Properties()
            extra.putAll(xmlParams)
            def config = new ConfigurationParser(extra, warnings).parseConfiguration(generatorFile)
            MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config,
                    new DefaultShellCallback(project.mbg.overwrite), warnings);

            myBatisGenerator.generate(new GradleProgressCallback(logger,
                    project.mbg.verbose), contextsToRun, fullyqualifiedTables);
        } catch (Exception e) {
            throw new GradleException(e.getMessage());
        }
        for (String error : warnings) {
            logger.warn(error);
        }
    }

    /**
     * 设置tableNames的额外信息
     * @param fullyqualifiedTables
     */
    private void setTableNamesValues(Set<String> fullyqualifiedTables, Project project) {
        if (StringUtility.stringHasValue(project.mbg.tableNames)) {
            StringTokenizer st = new StringTokenizer(project.mbg.tableNames, ","); //$NON-NLS-1$
            while (st.hasMoreTokens()) {
                String s = st.nextToken().trim();
                if (s.length() > 0) {
                    fullyqualifiedTables.add(s);
                }
            }
        }
    }

    /**
     * 设置contexts的额外信息
     * @param contextsToRun
     */
    private void setContextsValues(Set<String> contextsToRun, Project project) {
        if (StringUtility.stringHasValue(project.mbg.contexts)) {
            StringTokenizer st = new StringTokenizer(project.mbg.contexts, ","); //$NON-NLS-1$
            while (st.hasMoreTokens()) {
                String s = st.nextToken().trim();
                if (s.length() > 0) {
                    contextsToRun.add(s);
                }
            }
        }
    }

    /**
     * 基础验证并且设置内容到xmlParam中去
     */
    private void validation(Project project) {
        if (!generatorFile.exists()) {
            logger.error("generatorConfig.xml该文件不存在请查找目录是否存在:${generatorFile.getAbsolutePath()}");
            throw new FileNotFoundException(generatorFile.getAbsolutePath())
        }

        if (!javaProjectFile.exists()) {
            logger.error("javaProject目录不存在:${javaProjectFile.getAbsolutePath()}");
            throw new FileNotFoundException(javaProjectFile.getAbsolutePath())
        }
        xmlParams["javaProject"] = project.mbg.javaProject

        if (!resourcesProjectFile.exists()) {
            logger.error("resourcesProject目录不存在:${resourcesProjectFile.getAbsolutePath()}");
            throw new FileNotFoundException(resourcesProjectFile.getAbsolutePath())
        }
        xmlParams["resourcesProject"] = project.mbg.resourcesProject
        def errorMsg;
        //driver
        if (!StringUtility.stringHasValue(project.mbg.jdbc.driver)) {
            errorMsg = "变量mbg.jdbc.driver<String>未设置,请设置后重试."
            logger.error(errorMsg)
            throw new GradleException(errorMsg)
        }
        xmlParams["jdbc.driver"] = project.mbg.jdbc.driver
        //url
        if (!StringUtility.stringHasValue(project.mbg.jdbc.url)) {
            errorMsg = "变量mbg.jdbc.url<String>未设置,请设置后重试."
            logger.error(errorMsg)
            throw new GradleException(errorMsg)
        }
        xmlParams["jdbc.url"] = project.mbg.jdbc.url

        //username
        if (!StringUtility.stringHasValue(project.mbg.jdbc.username)) {
            errorMsg = "变量mbg.jdbc.username<String>未设置,请设置后重试."
            logger.error(errorMsg)
            throw new GradleException(errorMsg)
        }
        xmlParams["jdbc.username"] = project.mbg.jdbc.username

        //password
        if (!StringUtility.stringHasValue(project.mbg.jdbc.password)) {
            errorMsg = "变量mbg.jdbc.password<String>未设置,请设置后重试."
            logger.error(errorMsg)
            throw new GradleException(errorMsg)
        }
        xmlParams["jdbc.password"] = project.mbg.jdbc.password

        //plugins
        Class clazzPlugin = project.mbg.xml.mapperPlugin
        if (clazzPlugin == null) {
            errorMsg = "变量mbg.xml.mapperPlugin<Class<? extends PluginAdapter>>未设置,请设置后重试."
            logger.error(errorMsg)
            throw new GradleException(errorMsg)
        }
        xmlParams["xml.mapperPlugin"] = clazzPlugin.getName()

        if (!StringUtility.stringHasValue(project.mbg.xml.mapperMapper)) {
            errorMsg = "变量mbg.xml.mapperMapper<String>未设置,请设置后重试."
            logger.error(errorMsg)
            throw new GradleException(errorMsg)
        }
        xmlParams["xml.mapperMapper"] = project.mbg.xml.mapperMapper
        //
        if (!StringUtility.stringHasValue(project.mbg.xml.mapperPackage)) {
            errorMsg = "变量mbg.xml.mapperPackage<String>未设置,请设置后重试."
            logger.error(errorMsg)
            throw new GradleException(errorMsg)
        }
        xmlParams["xml.mapperPackage"] = project.mbg.xml.mapperPackage

        if (!StringUtility.stringHasValue(project.mbg.xml.modelPackage)) {
            errorMsg = "变量mbg.xml.modelPackage<String>未设置,请设置后重试."
            logger.error(errorMsg)
            throw new GradleException(errorMsg)
        }
        xmlParams["xml.modelPackage"] = project.mbg.xml.modelPackage

        if (!StringUtility.stringHasValue(project.mbg.xml.xmlPackage)) {
            errorMsg = "变量mbg.xml.xmlPackage<String>未设置,请设置后重试."
            logger.error(errorMsg)
            throw new GradleException(errorMsg)
        }
        xmlParams["xml.xmlPackage"] = project.mbg.xml.xmlPackage
    }
}
