/**
* @author John Abraham
* CS1550 - Operating Systems
* Project3: Virtual Memory Simulator
*
* This class represents a Page Table Entry (PTE)
* that will be used as part of the page table.
*/

public class PageTableEntry {

   int index;
   int frame;
   boolean valid;
   boolean dirty;
   boolean referenced;

   public PageTableEntry() {
      this.index = 0;
      this.frame = -1;
      this.valid = false;
      this.dirty = false;
      this.referenced = false;
   }

   public PageTableEntry(PageTableEntry copy) {
      this.index = copy.index;
      this.frame = copy.frame;
      this.valid = copy.valid;
      this.dirty = copy.dirty;
      this.referenced = copy.referenced;
   }
}