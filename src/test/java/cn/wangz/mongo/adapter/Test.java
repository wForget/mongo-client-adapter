package cn.wangz.mongo.adapter;

import cn.wangz.mongo.adapter.annotation.Document;
import cn.wangz.mongo.adapter.annotation.QueryField;
import cn.wangz.mongo.adapter.annotation.UpdateField;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author wang_zh
 * @date 2020/1/9
 */
public class Test {

    public static void main(String[] args) {
        Course course = new Course("Mathematics", 50, null);
        User user = new User("wangz", 20, course);
        System.out.println(user.query().toJson());
        System.out.println(user.update().toJson());
        // the printed results:
        // {"name": "wangz", "course.course": "Mathematics"}
        // {"$inc": {"course.score": 50}, "$set": {"name": "wangz", "age": 20, "course.course": "Mathematics"}}
    }

}

@Data
@Document
@AllArgsConstructor
class User extends BaseDoc {
    @QueryField
    @UpdateField("name")
    private String name;

    @UpdateField("age")
    private int age;

    @QueryField
    @UpdateField("course")
    private Course course;
}

@Data
@Document
@AllArgsConstructor
class Course {

    @QueryField
    @UpdateField("course")
    private String course;

    @UpdateField(value = "score", operator = "$inc")
    private int score;

    @UpdateField("extend")
    private String extend;

}
