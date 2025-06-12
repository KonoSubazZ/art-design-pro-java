/*
 * Proprietary Software License
 *
 * Copyright (c) 2025 iboot
 *
 * This software and its associated documentation ("Software") are proprietary property of iboot.
 * Without explicit written permission from iboot, no individual or entity may:
 *
 * 1. Copy, modify, merge, publish, distribute, sublicense, or sell copies of the Software;
 * 2. Reverse engineer, decompile, or disassemble the Software;
 * 3. Remove or alter any copyright notices or other proprietary markings in the Software;
 * 4. Use the Software for any commercial purposes.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL
 * IBOOT BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN
 * ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 * The Software may not be used without explicit written permission from iboot.
 * Author: tangsc.
 */

package com.iboot.studio.common.constant;

/** 全局通用常量类 */
public class Const {
  /**
   * 服务API路径（由于本项目旨在最小化部署资源需求，所以前端打包后的文件会直接放在 static 目录下，交由 SpringBoot 来进行 Server） 所以访问 后端 API Base
   * 的路径为：<a href="http://localhost:18080/api/iboot">Server API Path</a> 而前端的访问路径为：<a
   * href="http://localhost:18080/">前端API</a>
   * 如果前端使用 nginx 部署也不会有影响，因为 nginx 配置了反向代理，将前端的请求转发到后端的 API 路径下
   */
  public static final String SERVER_API_PATH = "/api/iboot";

  private Const() {}
}
