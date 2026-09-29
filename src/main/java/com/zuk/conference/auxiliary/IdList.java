package com.zuk.conference.auxiliary;

import java.util.ArrayList;
import java.util.List;

/**
 * Helpers for the comma separated id lists stored in CONFERENCE.ID_PARTICIPANT
 * and PARTICIPANT.ID_CONFERENCE_PARTICIPANT (format: "1,2,3,").
 */
public final class IdList {

    private IdList() {
    }

    public static List<Integer> parse(String ids) {
        List<Integer> result = new ArrayList<>();
        if (ids == null) {
            return result;
        }
        for (String part : ids.split(",")) {
            String trimmed = part.trim();
            if (trimmed.isEmpty()) {
                continue;
            }
            try {
                result.add(Integer.parseInt(trimmed));
            } catch (NumberFormatException ignored) {
                // skip garbage such as the old "null16," values
            }
        }
        return result;
    }

    public static String format(List<Integer> ids) {
        StringBuilder sb = new StringBuilder();
        for (Integer id : ids) {
            sb.append(id).append(',');
        }
        return sb.toString();
    }

    public static boolean contains(String ids, int id) {
        return parse(ids).contains(id);
    }

    public static String add(String ids, int id) {
        List<Integer> list = parse(ids);
        if (!list.contains(id)) {
            list.add(id);
        }
        return format(list);
    }

    public static String remove(String ids, int id) {
        List<Integer> list = parse(ids);
        list.removeIf(value -> value == id);
        return format(list);
    }
}
