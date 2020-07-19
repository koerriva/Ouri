package engine.scene.gltf;

import java.util.Base64;

public class GLTF_Buffer {
    private Integer byteLength;
    private String uri;
    private byte[] data;

    public Integer getByteLength() {
        return byteLength;
    }

    public void setByteLength(Integer byteLength) {
        this.byteLength = byteLength;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
        data = Base64.getDecoder().decode(uri.substring(37));
        System.out.println("buffer "+data.length);
    }

    public byte[] getData(){
        return data;
    }
}
