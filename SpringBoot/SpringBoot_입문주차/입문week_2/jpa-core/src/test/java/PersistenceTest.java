import com.sparta.entity.Memo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class PersistenceTest {

    EntityManagerFactory emf;
    EntityManager em;

    @BeforeEach
    void setUp() {
        emf = Persistence.createEntityManagerFactory("memo");
        em = emf.createEntityManager();
    }

    @Test
    @DisplayName("1차 캐시 : Entity 저장")
    void test1() {
        EntityTransaction et = em.getTransaction();
        et.begin();
        try {
            Memo memo = new Memo();
            memo.setId(1L);
            memo.setUsername("Robbie");
            memo.setContents("1차 캐시 Entity 저장");
            em.persist(memo);

            et.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            et.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }

    @Test
    @DisplayName("Entity 조회 : 캐시 저장소에 해당하는 Id가 존재하지 않은 경우")
    void test2() {
        try {
            Memo memo = em.find(Memo.class, 1);
            System.out.println("memo.getId() = " + memo.getId());
            System.out.println("memo.getUsername() = " + memo.getUsername());
            System.out.println("memo.getContents() = " + memo.getContents());
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            em.close();
        }
        emf.close();
    }

    @Test
    @DisplayName("Entity 조회 : 캐시 저장소에 해당하는 Id가 존재하는 경우")
    void test3() {
        try {
            Memo memo1 = em.find(Memo.class, 1);
            System.out.println("memo1 조회 후 캐시 저장소에 저장\n");
            Memo memo2 = em.find(Memo.class, 1);
            System.out.println("memo2.getId() = " + memo2.getId());
            System.out.println("memo2.getUsername() = " + memo2.getUsername());
            System.out.println("memo2.getContents() = " + memo2.getContents());
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            em.close();
        }
        emf.close();
    }

    @Test
    @DisplayName("객체 동일성 보장")
    void test4() {
        EntityTransaction et = em.getTransaction();
        et.begin();
        try {
            Memo memo3 = new Memo();
            memo3.setId(2L);
            memo3.setUsername("Robbert");
            memo3.setContents("객체 동일성 보장");
            em.persist(memo3);

            Memo memo1 = em.find(Memo.class, 1);    // select query
            Memo memo2 = em.find(Memo.class, 1);    // 1차 캐시
            Memo memo  = em.find(Memo.class, 2);    // 1차 캐시

            System.out.println(memo1 == memo2);         // true
            System.out.println("++ ====== ++");
            System.out.println("memo1 = " + System.identityHashCode(memo1));
            System.out.println("memo2 = " + System.identityHashCode(memo2));


            System.out.println(memo1 == memo);          // false

            et.commit();        // memo 와 memo3 insert query

        } catch (Exception ex) {
            ex.printStackTrace();
            et.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }

    @Test
    @DisplayName("Entity 삭제")
    void test5() {
        EntityTransaction et = em.getTransaction();
        et.begin();
        try {

            Memo memo = em.find(Memo.class, 2);
            em.remove(memo);

            et.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            et.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }

    @Test
    @DisplayName("쓰기 지연 저장소 확인")
    void test6() {
        EntityTransaction et = em.getTransaction();
        et.begin();
        try {
            Memo memo = new Memo();
            memo.setId(2L);
            memo.setUsername("Robbert");
            memo.setContents("쓰기 지연 저장소");
            em.persist(memo);

            Memo memo2 = new Memo();
            memo2.setId(3L);
            memo2.setUsername("Bob");
            memo2.setContents("과연 저장을 잘 하고 있을까?");
            em.persist(memo2);

            System.out.println("트랜잭션 commit 전");
            et.commit();
            System.out.println("트랜잭션 commit 후");

        } catch (Exception ex) {
            ex.printStackTrace();
            et.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }

    @Test
    @DisplayName("flush() 메서드 확인")
    void test7() {
        EntityTransaction et = em.getTransaction();
        et.begin();
        try {
            Memo memo = new Memo();
            memo.setId(4L);
            memo.setUsername("Flush");
            memo.setContents("Flush() 메서드 호출");
            em.persist(memo);

            System.out.println("flush() 전");
            em.flush(); // flush() 직접 호출
            System.out.println("flush() 후\n");

            System.out.println("트랜잭션 commit 전");
            et.commit();
            System.out.println("트랜잭션 commit 후");
        } catch (Exception ex) {
            ex.printStackTrace();
            et.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }

    @Test
    @DisplayName("변경 감지 확인")
    void test8() {
        EntityTransaction et = em.getTransaction();
        et.begin();
        try {
            System.out.println("변경할 데이터를 조회합니다.");
            Memo memo = em.find(Memo.class, 4);
            System.out.println("memo.getId() = " + memo.getId());
            System.out.println("memo.getUsername() = " + memo.getUsername());
            System.out.println("memo.getContents() = " + memo.getContents());

            System.out.println("\n수정을 진행합니다.");
            memo.setUsername("Update");
            memo.setContents("변경 감지 확인");

            System.out.println("트랜잭션 commit 전");
            et.commit();
            System.out.println("트랜잭션 commit 후");
        } catch (Exception ex) {
            ex.printStackTrace();
            et.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }
}
