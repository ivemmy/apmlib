package com.apm.model;

/**
 * Activity信息类
 *
 * @author 王俊超
 */
public class ActivityInfo extends Base {
    // Activity的名称
    private String name;

    // 当前的activity是从哪一个Activity跳转来的
    private String from;

    // activity的开始创建时间
    private Long beforeCreate;

    // activity的创建完成时间
    private Long afterCreate;

    // activity被其它activity覆盖或者退出的时间
    private Long afterPause;

    public ActivityInfo() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public Long getBeforeCreate() {
        return beforeCreate;
    }

    public void setBeforeCreate(Long beforeCreate) {
        this.beforeCreate = beforeCreate;
    }

    public Long getAfterCreate() {
        return afterCreate;
    }

    public void setAfterCreate(Long afterCreate) {
        this.afterCreate = afterCreate;
    }

    public Long getAfterPause() {
        return afterPause;
    }

    public void setAfterPause(Long afterPause) {
        this.afterPause = afterPause;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ActivityInfo other = (ActivityInfo) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }

/*	@Override
	public String toString() {
		return name + ", " + beforeCreate + ", " + afterCreate + ", " + (afterCreate - beforeCreate);
	}*/

    @Override
    public String toString() {
        return "ActivityInfo{" +
                "name='" + name + '\'' +
                ", from='" + from + '\'' +
                ", beforeCreate=" + beforeCreate +
                ", afterCreate=" + afterCreate +
                ", afterPause=" + afterPause +
                '}';
    }
}
