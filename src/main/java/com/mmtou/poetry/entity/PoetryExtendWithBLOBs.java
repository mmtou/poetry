package com.mmtou.poetry.entity;

import java.io.Serializable;

/**
 * poetry_extend
 * @author 
 */
public class PoetryExtendWithBLOBs extends PoetryExtend implements Serializable {
    /**
     * 翻译
     */
    private String translation;

    /**
     * 评析
     */
    private String intro;

    /**
     * 注释
     */
    private String annotation;

    /**
     * 赏析
     */
    private String appreciation;

    private static final long serialVersionUID = 1L;

    public String getTranslation() {
        return translation;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getAnnotation() {
        return annotation;
    }

    public void setAnnotation(String annotation) {
        this.annotation = annotation;
    }

    public String getAppreciation() {
        return appreciation;
    }

    public void setAppreciation(String appreciation) {
        this.appreciation = appreciation;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        PoetryExtendWithBLOBs other = (PoetryExtendWithBLOBs) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getPoetryId() == null ? other.getPoetryId() == null : this.getPoetryId().equals(other.getPoetryId()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getCreateBy() == null ? other.getCreateBy() == null : this.getCreateBy().equals(other.getCreateBy()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
            && (this.getUpdateBy() == null ? other.getUpdateBy() == null : this.getUpdateBy().equals(other.getUpdateBy()))
            && (this.getDeleteFlag() == null ? other.getDeleteFlag() == null : this.getDeleteFlag().equals(other.getDeleteFlag()))
            && (this.getTranslation() == null ? other.getTranslation() == null : this.getTranslation().equals(other.getTranslation()))
            && (this.getIntro() == null ? other.getIntro() == null : this.getIntro().equals(other.getIntro()))
            && (this.getAnnotation() == null ? other.getAnnotation() == null : this.getAnnotation().equals(other.getAnnotation()))
            && (this.getAppreciation() == null ? other.getAppreciation() == null : this.getAppreciation().equals(other.getAppreciation()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getPoetryId() == null) ? 0 : getPoetryId().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getCreateBy() == null) ? 0 : getCreateBy().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getUpdateBy() == null) ? 0 : getUpdateBy().hashCode());
        result = prime * result + ((getDeleteFlag() == null) ? 0 : getDeleteFlag().hashCode());
        result = prime * result + ((getTranslation() == null) ? 0 : getTranslation().hashCode());
        result = prime * result + ((getIntro() == null) ? 0 : getIntro().hashCode());
        result = prime * result + ((getAnnotation() == null) ? 0 : getAnnotation().hashCode());
        result = prime * result + ((getAppreciation() == null) ? 0 : getAppreciation().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", translation=").append(translation);
        sb.append(", intro=").append(intro);
        sb.append(", annotation=").append(annotation);
        sb.append(", appreciation=").append(appreciation);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}