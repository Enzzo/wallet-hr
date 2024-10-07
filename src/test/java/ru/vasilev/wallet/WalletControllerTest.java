package ru.vasilev.wallet;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.vasilev.wallet.controller.WalletController;
import ru.vasilev.wallet.exception.WalletNotFoundException;
import ru.vasilev.wallet.service.WalletService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.UUID;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(WalletController.class)
public class WalletControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private WalletService walletService;

	@Test
	public void testGetWalletBalance_Success() throws Exception {
		UUID walletId = UUID.randomUUID();
		BigDecimal balance = new BigDecimal("2500.00");

		Mockito.when(walletService.getWalletBalance(walletId)).thenReturn(balance);

		mockMvc.perform(get("/api/v1/wallets/{walletId}", walletId))
				.andExpect(status().isOk())
				.andExpect(content().string(balance.toString()));
	}

	@Test
	public void testGetWalletBalance_WalletNotFound() throws Exception {
		UUID walletId = UUID.randomUUID();

		Mockito.when(walletService.getWalletBalance(walletId))
				.thenThrow(new WalletNotFoundException("Wallet not found"));

		mockMvc.perform(get("/api/v1/wallets/{walletId}", walletId))
				.andExpect(status().isNotFound())
				.andExpect(content().string("Wallet not found"));
	}
}