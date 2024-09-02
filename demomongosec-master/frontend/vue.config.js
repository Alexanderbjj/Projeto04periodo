const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,
  outputDir: '..\\demo\\src\\main\\resources\\static',
  configureWebpack: {
    devtool: 'source-map'
  }
})
