
package resume_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import resume_backend.entity.Resume;

public interface ResumeRepository extends JpaRepository<Resume, Long> {
}
