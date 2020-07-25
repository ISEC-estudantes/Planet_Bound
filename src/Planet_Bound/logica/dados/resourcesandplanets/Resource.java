package Planet_Bound.logica.dados.resourcesandplanets;


/**
 * @brief Lista de recursos possiveis de obter neste jogo.
 * <p>
 * Neste trabalho foi considerado que os recursos sao todos os que estao aqui listados excepto quando é explicito os a escolher.
 */
public enum Resource {
    Red, //! planet and resource
    Green, //! planet and resource
    Blue, //! planet and resource
    Black, //! planet and resource
    Fuel, //! fuel
    Ammo, //! ammo
    Shield,   //! shild
    Artifact  //! artifact
}
/*
    Red , //!planet and resource
    Green, //!planet and resource
    Blue, //!planet and resource
    Black, //!planet and resource
    Fuel, //!fuel system
    Weapon, //!weapon system
    Shild,   //!shild system
    Artifact; //!artifact
    static Random random = new Random();
    private static final List<Resource> VALUES = Collections.unmodifiableList(Arrays.asList(values()));

    private static final int SIZE = VALUES.size();
    static public List<Resource> getValues(){return VALUES;}
    //!public static Resource randomResource()  { zreturn VALUES.get(ShipDados.random.nextInt(SIZE)); }

 */