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

package com.iboot.studio.infrastructure.integration.minioplus;

import java.util.Collections;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.liuxp.minioplus.api.model.dto.FileMetadataInfoDTO;
import org.liuxp.minioplus.api.model.dto.FileMetadataInfoSaveDTO;
import org.liuxp.minioplus.api.model.dto.FileMetadataInfoUpdateDTO;
import org.liuxp.minioplus.api.model.vo.FileMetadataInfoVo;
import org.liuxp.minioplus.core.repository.MetadataRepository;
import org.springframework.stereotype.Service;

/** 文件元数据接口实现类 */
@Slf4j
@Service
public class MetadataRepositoryImpl implements MetadataRepository {

  @Override
  public List<FileMetadataInfoVo> list(FileMetadataInfoDTO searchDTO) {
    return Collections.emptyList();
  }

  @Override
  public FileMetadataInfoVo one(FileMetadataInfoDTO searchDTO) {
    return null;
  }

  @Override
  public FileMetadataInfoVo save(FileMetadataInfoSaveDTO saveDTO) {
    return null;
  }

  @Override
  public FileMetadataInfoVo update(FileMetadataInfoUpdateDTO updateDTO) {
    return null;
  }

  @Override
  public Boolean remove(Long id) {
    return true;
  }
}
