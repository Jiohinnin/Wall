import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;



interface Structure {
    Optional<Block> findBlockByColor(String color);
    List<Block> findBlocksByMaterial(String material);
    int countWithCompositeBlocksComponents();
    int count();
}

public class Wall implements Structure {
    private List<Block> blocks;

    public Wall(List<Block> blocks){
         setBlocks(blocks);
    }

    @Override
    public Optional<Block> findBlockByColor(String color) {
        return blocks.stream().filter(b -> b.getColor().equals(color)).findFirst();
    }

    @Override
    public List<Block> findBlocksByMaterial(String material) {
        return blocks.stream().filter(b -> b.getMaterial().equals(material)).collect(Collectors.toList());
    }

    @Override
    public int count() {
        return blocks.size();
    }


    @Override
    public int countWithCompositeBlocksComponents() {
        int counter = 0;
        for (int i = 0; i < blocks.size(); i++) {
            if(blocks.get(i) instanceof CompositeBlockImpl){
                CompositeBlockImpl compositeBlockImpl = (CompositeBlockImpl) blocks.get(i);
                counter++;
                for (int j = 0; j < compositeBlockImpl.getBlocks().size() ; j++) {
                    counter++;
                }
            } else {
            counter++;
            }
        }
        return counter;
    }

    public void setBlocks(List<Block> blocks){
        this.blocks = blocks.stream().map(b -> b.clone()).collect(Collectors.toList());
    }
}

 
interface Block {
    String getColor();
    String getMaterial();
    Block clone();
}

class BlockImpl implements Block{
    private String color;
    private String material;
    
   
    public BlockImpl(String color, String material) {
        setColor(color);;
        setMaterial(material);
    }

    @Override
    public String getColor(){
        return this.color;
    }

    public void setColor(String color){
        this.color = color;
    }

    @Override
    public String getMaterial(){
        return this.material;
    }

    public void setMaterial(String material){
        this.material = material;
    }

    @Override
    public BlockImpl clone() {
        return new BlockImpl(this.getColor(), this.getMaterial());
    }  
}

interface CompositeBlock extends Block {
    List<Block> getBlocks();
}

class CompositeBlockImpl implements CompositeBlock{
    private String color;
    private String material;
    private List<Block> blocks;

    public CompositeBlockImpl(String color, String material, List<Block> blocks){
        setColor(color);
        setMaterial(material);
        setBlocks(blocks);
    }
    
    @Override
    public String getColor() {
        return this.color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public String getMaterial() {
        return this.material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    @Override
    public List<Block> getBlocks() {
        return blocks.stream().map(b -> b.clone()).collect(Collectors.toList());

    }

    public void setBlocks(List<Block> blocks){
        this.blocks = blocks.stream().map(b -> b.clone()).collect(Collectors.toList());
    }

    @Override
    public CompositeBlockImpl clone() {
        return new CompositeBlockImpl(this.getColor(), this.getMaterial(), this.getBlocks());
    }
}
