const path = require("path");

const HTMLWebpackPlugin = require("html-webpack-plugin");
const MiniCssExtractPlugin = require("mini-css-extract-plugin");
const webpack = require("webpack");

module.exports = (env) => {
  const isProduction = env.START_MODE === "production";
  console.log(isProduction);
  return {
    mode: isProduction ? "production" : "development",
    entry: "./src/index.tsx",

    output: {
      path: path.join(__dirname, "/dist")
    },

    plugins: [
      new HTMLWebpackPlugin({
        template: "./public/index.html"
      }),
      new MiniCssExtractPlugin(),
      new webpack.DefinePlugin({
        process: {
          env: {}
        }
      })
    ],

    devServer: {
      historyApiFallback: true
    },

    module: {
      rules: [
        {
          test: /\.(ts|tsx)$/,
          exclude: /node_modules/,
          use: ["babel-loader", "ts-loader"],
          resolve: {
            extensions: [".ts", ".tsx", ".js", ".json"]
          }
        },
        {
          test: /\.scss$/,
          use: [{ loader: "style-loader" }, { loader: "css-loader", options: { modules: true } }, { loader: "sass-loader" }]
        },
        {
          test: /\.(png|jpe?g|gif)$/i,
          use: [
            {
              loader: "file-loader"
            }
          ]
        }
      ]
    },
    devtool: isProduction ? "source-map" : "eval",
    optimization: {
      minimize: true
    }
  };
};
