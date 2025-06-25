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

package com.novo.report.common.util;

import cn.hutool.core.bean.BeanUtil;
import com.novo.report.common.constant.Const;
import com.novo.report.common.constant.ResourceTypeEnum;
import com.novo.report.infrastructure.persistence.entity.Resource;
import com.novo.report.web.vo.UserMenu;
import java.util.*;

public class MenuUtil {

  private MenuUtil() {}

  /**
   * 将请求路径转换为资源编码
   *
   * @param requestPath 请求路径
   * @return 资源编码
   */
  public static String requestPath2ResourceCode(String requestPath) {
    if (requestPath == null) {
      return "";
    }
	  return requestPath.replace(Const.SERVER_API_PATH + "/", "");
  }

  /**
   * 将系统资源转为用户菜单（树结构）
   *
   * @param resources 资源列表
   * @return 用户菜单树结构
   */
  public static List<UserMenu> generateUserMenus(List<Resource> resources) {
    if (resources == null || resources.isEmpty()) {
      return new ArrayList<>();
    }

    // 按sortOrder排序
    List<Resource> sortedResources =
        resources.stream()
            .sorted(
                Comparator.comparing(
                    Resource::getSortOrder, Comparator.nullsLast(String::compareTo)))
            .toList();

    // 构建父节点到子节点的映射，不包括按钮/接口类型的资源
    Map<String, List<Resource>> parentChildrenMap = new HashMap<>();
    // 构建父节点到按钮/接口资源的映射
    Map<String, List<Resource>> parentButtonMap = new HashMap<>();

    for (Resource resource : sortedResources) {
      String parentId = resource.getParentId();
      if (parentId == null) {
        parentId = "";
      }

      // 按钮/接口类型的资源单独处理
      if (ResourceTypeEnum.BUTTON.getCode().equals(resource.getResourceType())) {
        parentButtonMap.computeIfAbsent(parentId, k -> new ArrayList<>()).add(resource);
      } else {
        // 非按钮/接口类型的资源正常处理
        parentChildrenMap.computeIfAbsent(parentId, k -> new ArrayList<>()).add(resource);
      }
    }

    // 构建根节点菜单
    return buildMenuTree("0", parentChildrenMap, parentButtonMap);
  }

  /**
   * 递归构建菜单树
   *
   * @param parentId 父节点ID
   * @param parentChildrenMap 父节点到子节点的映射
   * @param parentButtonMap 父节点到按钮/接口资源的映射
   * @return 菜单树
   */
  private static List<UserMenu> buildMenuTree(
      String parentId,
      Map<String, List<Resource>> parentChildrenMap,
      Map<String, List<Resource>> parentButtonMap) {
    List<Resource> children = parentChildrenMap.get(parentId);
    if (children == null || children.isEmpty()) {
      return Collections.emptyList();
    }

    List<UserMenu> menuList = new ArrayList<>();
    for (Resource resource : children) {
      UserMenu menu = convertResourceToUserMenu(resource);

      // 处理该资源下的按钮/接口资源，添加到authList
      List<Resource> buttons = parentButtonMap.get(resource.getResourceId());
      if (buttons != null && !buttons.isEmpty()) {
        List<UserMenu.Auth> authList = new ArrayList<>();
        for (Resource button : buttons) {
          UserMenu.Auth auth =
              new UserMenu.Auth()
                  .setId(button.getResourceId())
                  .setTitle(button.getResourceName())
                  .setAuthMark(button.getResourceCode());
          authList.add(auth);
        }
        menu.getMeta().setAuthList(authList);
      }

      // 递归构建子菜单
      List<UserMenu> childMenus =
          buildMenuTree(resource.getResourceId(), parentChildrenMap, parentButtonMap);
      if (!childMenus.isEmpty()) {
        menu.setChildren(childMenus);
      }

      menuList.add(menu);
    }

    return menuList;
  }

  /**
   * 将Resource对象转换为UserMenu对象
   *
   * @param resource Resource对象
   * @return UserMenu对象
   */
  private static UserMenu convertResourceToUserMenu(Resource resource) {
    UserMenu userMenu = new UserMenu();
    userMenu.setId(resource.getResourceId());
    userMenu.setPath(resource.getResourceCode());
    userMenu.setName(resource.getResourceName());
    userMenu.setComponent(resource.getComponentPath());
    // 设置Meta信息
    UserMenu.Meta meta = BeanUtil.toBean(resource, UserMenu.Meta.class);
    userMenu.setMeta(meta);
    return userMenu;
  }
}
