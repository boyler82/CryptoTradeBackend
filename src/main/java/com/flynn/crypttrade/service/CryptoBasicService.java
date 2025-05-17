package com.flynn.crypttrade.service;

import com.flynn.crypttrade.client.CoinGeckoClient;
import com.flynn.crypttrade.domain.CryptoBasic;
import com.flynn.crypttrade.dto.CoinGeckoCryptoDto;
import com.flynn.crypttrade.dto.CryptoBasicDto;
import com.flynn.crypttrade.mapper.CryptoBasicMapper;
import com.flynn.crypttrade.repository.CryptoBasicRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CryptoBasicService {

    private final CoinGeckoClient coinGeckoClient;
    private final CryptoBasicRepository cryptoBasicRepository;

    public CryptoBasic saveCrypto(String cryptoId) {
        CoinGeckoCryptoDto dto = coinGeckoClient.fetchCryptoBasic(cryptoId);

        CryptoBasic cryptoBasic = new CryptoBasic();
        cryptoBasic.setExternalId(dto.getExternalId());
        cryptoBasic.setSymbol(dto.getSymbol());
        cryptoBasic.setName(dto.getName());
        cryptoBasic.setImageUrl(dto.getImageUrl());
        cryptoBasic.setCurrentPrice(dto.getCurrentPrice());
        cryptoBasic.setHigh24h(dto.getHigh24h());
        cryptoBasic.setLow24h(dto.getLow24h());
        cryptoBasic.setPriceChange24h(dto.getPriceChange24h());

        return cryptoBasicRepository.save(cryptoBasic);
    }

    public void fetchAllCryptos() {
        List<CoinGeckoCryptoDto> list = coinGeckoClient.fetchAllCryptosBasic();

        list.forEach(dto -> {
            CryptoBasic crypto = new CryptoBasic();
            crypto.setExternalId(dto.getExternalId());
            crypto.setSymbol(dto.getSymbol());
            crypto.setName(dto.getName());
            crypto.setImageUrl(dto.getImageUrl());
            crypto.setCurrentPrice(dto.getCurrentPrice());
            crypto.setHigh24h(dto.getHigh24h());
            crypto.setLow24h(dto.getLow24h());
            crypto.setPriceChange24h(dto.getPriceChange24h());

            cryptoBasicRepository.save(crypto);
        });
    }


    public List<CryptoBasicDto> getAllCryptos() {
        return cryptoBasicRepository.findAll()
                .stream()
                .map(CryptoBasicMapper::toDto)
                .toList();
    }
}