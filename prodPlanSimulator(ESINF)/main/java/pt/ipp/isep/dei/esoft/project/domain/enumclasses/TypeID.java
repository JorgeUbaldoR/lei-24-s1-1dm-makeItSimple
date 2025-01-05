package pt.ipp.isep.dei.esoft.project.domain.enumclasses;

/**
 * Represents different types of IDs used in the system.
 * This enum defines two types of IDs: MACHINE and ITEM.
 */
public enum TypeID {
    MACHINE{
        public String toString() {
            return "W";
        }
    },
    ITEM{
        public String toString() {
            return "I";
        }
    },
    OPERATION{
        public String toString() {return "O";}
    },
    ACTIVITY{
        public String toString(){return "A";}
    },
    GRAPH{
        public String toString(){
            return "G";
        }
    },
    ORDER{
        public String toString(){return "O";}
    }
}
