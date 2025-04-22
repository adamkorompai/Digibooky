package com.switchfully.digibooky.api.dtos;

import com.switchfully.digibooky.api.dtos.mapper.MemberMapper;
import com.switchfully.digibooky.api.dtos.mapper.RentalMapper;
import com.switchfully.digibooky.domain.Member;
import com.switchfully.digibooky.domain.Rental;
import com.switchfully.digibooky.domain.Role;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

public class RentalMapperTest {

    private final RentalMapper rentalMapper = new RentalMapper();

    @Test
    public void mapToRentalDto_shouldConvertToRentalDtoCorrectly() {
        LocalDate rentalDate = LocalDate.of(2025,4,22);
        LocalDate returnDate = LocalDate.of(2025,5,7);
        Rental rental = new Rental("978-1-23-456789-7","1", rentalDate,returnDate);
        RentalDto result = rentalMapper.map(rental);

        assertThat(result).isNotNull();
        assertThat(result.getBookIsbn()).isEqualTo("978-1-23-456789-7");
        assertThat(result.getUserId()).isEqualTo("1");
        assertThat(result.getRentalDate()).isEqualTo(rentalDate);
        assertThat(result.getDueDate()).isEqualTo(returnDate);
    }

    @Test
    public void mapDtoToRental_shouldConvertDtoToRentalCorrectly() {

        LocalDate rentalDate = LocalDate.of(2025,4,22);
        LocalDate returnDate = LocalDate.of(2025,5,7);

        CreateRentalDto dto = new CreateRentalDto();
        dto.setBookIsbn("978-1-23-456789-7");
        dto.setUserId("1");
        dto.setRentDate(rentalDate);

        Rental result = rentalMapper.map(dto, returnDate);

        assertThat(result).isNotNull();
        assertThat(result.getBookIsbn()).isEqualTo("978-1-23-456789-7");
        assertThat(result.getUserId()).isEqualTo("1");
        assertThat(result.getRentalDate()).isEqualTo(rentalDate);
        assertThat(result.getDueDate()).isEqualTo(returnDate);


    }
}
