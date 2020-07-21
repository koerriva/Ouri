package engine.scene;

import engine.graph.Mesh;

import java.util.List;

public class Model extends Node {
    public Model(String name, List<Mesh> meshes) {
        super(name, meshes);
    }
}
