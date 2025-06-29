package klj.project.domain.util;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class IpBlock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ip_id")
    private Long id;

    private String  ipAddress;



    @Builder
    public IpBlock(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public static IpBlock createIpBlock (
           String ipAddress
    ){
        return IpBlock.builder()
                .ipAddress(ipAddress)
                .build();
    }

}
