package com.luckmerlin.file;

public final class NasPath extends Path{
    private String name;
    private String parent;
    private String host;
    private int port;
    private long size;
    private long length;
    private long modifyTime;
    private String extension;
    private String mime;
    private String pathSep;
    private int permissions;

    public NasPath setExtension(String extension) {
        this.extension = extension;
        return this;
    }

    public NasPath setHost(String host) {
        this.host = host;
        return this;
    }

    public NasPath setLength(long length) {
        this.length = length;
        return this;
    }

    public NasPath setMime(String mime) {
        this.mime = mime;
        return this;
    }

    public NasPath setModifyTime(long modifyTime) {
        this.modifyTime = modifyTime;
        return this;
    }

    public NasPath setName(String name) {
        this.name = name;
        return this;
    }

    public NasPath setParent(String parent) {
        this.parent = parent;
        return this;
    }

    public NasPath setPathSep(String pathSep) {
        this.pathSep = pathSep;
        return this;
    }

    public NasPath setPermissions(int permissions) {
        this.permissions = permissions;
        return this;
    }

    public NasPath setPort(int port) {
        this.port = port;
        return this;
    }

    public NasPath setSize(long size) {
        this.size = size;
        return this;
    }

    public final String getHostPort(){
        String host=this.host;
        return null!=host?host+":"+port:null;
    }

    @Override
    public String getParent() {
        return parent;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getExtension() {
        return extension;
    }

    @Override
    public String getSep() {
        return pathSep;
    }

    @Override
    public long getModifyTime() {
        return modifyTime;
    }

    @Override
    public String getMime() {
        return mime;
    }

    @Override
    public long getLength() {
        return length;
    }

    @Override
    public int getPermission() {
        return permissions;
    }

    @Override
    public boolean isDirectory() {
        return size>=0;
    }

    @Override
    public long getTotal() {
        return size;
    }
}
