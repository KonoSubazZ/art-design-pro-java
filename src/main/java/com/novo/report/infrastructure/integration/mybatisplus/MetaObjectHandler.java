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

package com.novo.report.infrastructure.integration.mybatisplus;

import cn.hutool.core.lang.func.LambdaUtil;
import com.novo.report.common.util.UserUtil;
import com.novo.report.infrastructure.persistence.entity.BaseEntity;
import com.novo.report.infrastructure.persistence.entity.User;
import java.time.LocalDateTime;

import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

@Component
public class MetaObjectHandler implements com.baomidou.mybatisplus.core.handlers.MetaObjectHandler {
  @Override
  public void insertFill(MetaObject metaObject) {
    User currentUser = UserUtil.getCurrentUser();
    this.strictInsertFill(
        metaObject,
        LambdaUtil.getFieldName(BaseEntity::getCreateTime),
        LocalDateTime.class,
        LocalDateTime.now());
    this.strictInsertFill(
        metaObject,
        LambdaUtil.getFieldName(BaseEntity::getCreatorId),
        String.class,
        currentUser.getUserId());
    this.strictInsertFill(
        metaObject,
        LambdaUtil.getFieldName(BaseEntity::getCreatorName),
        String.class,
        currentUser.getRealName());
  }

  @Override
  public void updateFill(MetaObject metaObject) {
    User currentUser = UserUtil.getCurrentUser();
    this.strictUpdateFill(
        metaObject,
        LambdaUtil.getFieldName(BaseEntity::getUpdateTime),
        LocalDateTime.class,
        LocalDateTime.now());
    this.strictUpdateFill(
        metaObject,
        LambdaUtil.getFieldName(BaseEntity::getUpdaterId),
        String.class,
        currentUser.getUserId());
    this.strictUpdateFill(
        metaObject,
        LambdaUtil.getFieldName(BaseEntity::getUpdaterName),
        String.class,
        currentUser.getRealName());
  }
}
