package Ejercicio.Puntos2d;

public interface Vec2d {
    //Esta interfaz generalizara el tmaño de los vectores de dos dimensiones

    void set(Vec2d vec2d);
    //Este metodo añadira los componentes al vector, estos se pasarán por parametro

    void add(Vec2d vec2d);
    // Este metodo añadira los componentes al vector, estos se pasarán por parametro
    void sub(Vec2d vec2d);
    //Este metodo multiplicara los componentes al vector, estos se pasarán por parametro
    void multiply(Vec2d vec2d);
    //Este metodo dividira los componentes al vector, estos se pasarán por parametro
    void divide(Vec2d vec2d);
    //Este metodo normalizara los componentes del vector, devolvera un nuevvo vetor pero normalizado
    void normalize();
    Vec2d normal();

    void translateThisAngle(float angle);

}
