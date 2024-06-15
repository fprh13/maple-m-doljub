package maple.doljub.common.util;

import maple.doljub.dto.maple.EquipmentItemDto;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 캐쉬 아이템 , 장신구 를 포함 할 시 필터링 조건에 추가 할 것
 */
public class EquipmentItemFilterUtil {

    /**
     * 장비 아이템을 필터링
     */
    public static List<EquipmentItemDto> filter(List<EquipmentItemDto> items) {
        return items.stream()
                .filter(item -> "SubWeapon 보조무기".equals(item.getEquipmentPageName()) ||
                        "Weapon 무기".equals(item.getEquipmentPageName()) ||
                        "Cap 모자".equals(item.getEquipmentPageName()) ||
                        "LongCoat 한벌옷".equals(item.getEquipmentPageName()) ||
                        "Gloves 장갑".equals(item.getEquipmentPageName()) ||
                        "Shoes 신발".equals(item.getEquipmentPageName()) ||
                        "Shoulder 어깨".equals(item.getEquipmentPageName()) ||
                        "Belt 벨트".equals(item.getEquipmentPageName()) ||
                        "Cape 망토".equals(item.getEquipmentPageName()) ||
                        "Pocket 포켓".equals(item.getEquipmentPageName()))
                .collect(Collectors.toList());
    }
}
