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

package com.novo.report.infrastructure.integration.concept.download;

import cn.hutool.core.convert.Convert;
import com.github.linyuzai.download.core.context.DownloadContext;
import com.github.linyuzai.download.core.options.DownloadOptions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DownloadLifecycleListener
    implements com.github.linyuzai.download.core.event.DownloadLifecycleListener {

  /** 下载开始。 */
  @Override
  public void onStart(DownloadContext context) {}

  /** 下载完成。 */
  @Override
  public void onComplete(DownloadContext context) {
    DownloadOptions downloadOptions = context.get(DownloadOptions.class);
    String source = Convert.toStr(downloadOptions.getSource());
    log.info("文件下载完成，删除临时文件:{}", source);
    //    FileUtil.del(source);
  }
}
