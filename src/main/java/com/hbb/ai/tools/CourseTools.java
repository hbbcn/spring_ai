package com.hbb.ai.tools;

import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.hbb.ai.entity.Course;
import com.hbb.ai.entity.CourseReservation;
import com.hbb.ai.entity.School;
import com.hbb.ai.entity.query.CourseQuery;
import com.hbb.ai.service.ICourseReservationService;
import com.hbb.ai.service.ICourseService;
import com.hbb.ai.service.ISchoolService;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.tool.annotation.Tool;

import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class CourseTools {

    private final ICourseService courseService;
    private final ISchoolService schoolService;
    private final ICourseReservationService courseReservationService;

    @Tool(description = "根据条件查询课程")
    public List<Course> queryCourse(CourseQuery query){
        if (query != null){
            QueryChainWrapper<Course> wrapper = courseService.query()
                    .eq(query.getType() != null, "type", query.getType())
                    .le(query.getEdu() != null, "edu", query.getEdu());
            if (query.getSorts() != null){
                for (CourseQuery.Sort sort : query.getSorts()) {
                    wrapper.orderBy(true, sort.getAsc(),sort.getField());
                }
            }
            return wrapper.list();
        }
        return courseService.list();
    }

    @Tool(description = "查询所有校区")
    public List<School> querySchool(){
        return schoolService.list();
    }

    @Tool(description = "生成预约单，返回预约单号")
    public Integer createCourseReservation(
            @ToolParam(description = "预约课程") String course,
            @ToolParam(description = "预约校区") String school,
            @ToolParam(description = "学生姓名") String studentName,
            @ToolParam(description = "联系电话") String contactInfo,
            @ToolParam(description = "备注", required = false) String remark) {
        CourseReservation reservation = new CourseReservation();
        reservation.setCourse(course);
        reservation.setSchool(school);
        reservation.setStudentName(studentName);
        reservation.setContactInfo(contactInfo);
        reservation.setRemark(remark);
        courseReservationService.save(reservation);

        return reservation.getId();
    }

}
