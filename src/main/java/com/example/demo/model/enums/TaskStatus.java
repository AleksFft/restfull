package com.example.demo.model.enums;

import com.example.demo.model.common.ClientNameable;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TaskStatus implements ClientNameable {
    NEW("Новая"),

    INIT("Инициализация"),
    INIT_DONE("Инициализация завершена успешно"),
    INIT_FAIL("Инициализация завершена не успешно"),

    PRE_VALIDATION("Проверка условий"),
    PRE_VALIDATION_FAIL("Проверка условий не пройдена"),
    PRE_VALIDATION_DONE("Проверка условий пройдена"),
    PRE_VALIDATION_DONE_WITH_ERRORS("Проверка условий пройдена частично"),

    BLOCKING("Блокировка"),
    BLOCKING_SKIPPED("Блокировка пропущена"),
    BLOCKING_FAIL("Ошибка при блокировке"),
    BLOCKING_DONE("Блокировка выполнена"),

    GENERATION("Генерация данных"),
    GENERATION_DONE("Генерация данных завершена"),
    GENERATION_FAIL("Генерация данных не выполнена"),
    GENERATION_SKIPPED("Генерация данных пропущена"),

    PREPARE("Подготовка данных"),
    PREPARE_FAIL("Подготовка данных не выполнена"),
    PREPARE_DONE("Подготовка данных завершена"),
    PREPARE_DONE_WITH_ERRORS("Подготовка данных завершена с ошибками"),

    MIGRATE("Миграция выполняется"),
    MIGRATE_FAIL("Миграция не выполнена"),
    MIGRATE_DONE("Миграция выполнена"),
    MIGRATE_DONE_WITH_ERRORS("Миграция завершена с ошибками"),
    SAVE_DATA_FAIL("Ошибка выгрузки"),

    H2H_MIGRATION("Миграция статусов документов в адаптеры h2h"),
    H2H_MIGRATION_DONE("Миграция статусов документов в адаптеры h2h выполнена"),
    H2H_MIGRATION_DONE_WITH_ERRORS("Миграция статусов документов в h2h завершена с ошибками"),
    H2H_MIGRATION_FAIL("Миграции статусов документов в адаптеры h2h не выполнена"),
    H2H_MIGRATION_SUSPENDED("Миграция статутов документов в адаптеры h2h приостановлена"),

    SLX_ID_REQUEST("Запрос идентификаторов клиентов в ПКБ"),
    SLX_ID_REQUEST_DONE("Запрос идентификаторов клиентов в ПКБ завершен успешно"),
    SLX_ID_REQUEST_DONE_WITH_ERRORS("Запрос идентификаторов клиентов в ПКБ завершен с ошибками"),

    ORGANIZATION_CS_REQUEST("Запрос идентификаторов клиентов в Карточку ЮЛ"),
    ORGANIZATION_CS_REQUEST_DONE_WITH_ERRORS("Запрос идентификаторов клиентов в Карточку ЮЛ завершен с ошибками"),
    ORGANIZATION_CS_REQUEST_DONE("Запрос идентификаторов клиентов в Карточку ЮЛ завершен успешно"),

    CANCELED("Остановлена"),

    ROLLBACK("Выполняется отмена миграции"),
    PARTIAL_ROLLBACK("Выполняется частичная отмена миграции"),
    ROLLBACK_FAIL("Ошибка отмены миграции"),
    ROLLBACK_DONE("Отмена миграции завершена"),
    ROLLBACK_DONE_PARTIALLY("Отмена миграции выполнена частично"),

    UNBLOCKING("Разблокировка"),
    UNBLOCKING_SKIPPED("Разблокировка пропущена"),
    UNBLOCKING_FAIL("Ошибка при разблокировке"),
    UNBLOCKING_DONE("Разблокировка выполнена"),

    DELETE("Выполняется предварительное удаление"),
    DELETE_SKIPPED("Предварительное удаление пропущено"),
    DELETE_DONE("Предварительное удаление завершено"),
    DELETE_DONE_WITH_ERRORS("Предварительное удаление завершено с ошибками"),

    DELETE_INIT("Инициализация удаления"),
    DELETE_INIT_DONE("Инициализация удаления завершена успешно"),
    DELETE_INIT_FAIL("Инициализация удаления завершена не успешно"),

    MIGRATE_DONE_PARTIALLY("Миграция выполнена частично"),

    // фоновая миграция
    DBO2_CLIENT_SEARCH("Запрос учетных данных в СДБО 2.0"),
    DBO2_CLIENT_SEARCH_DONE("Запрос учетных данных в СДБО 2.0 завершен"),
    DBO2_CLIENT_SEARCH_DONE_WITH_ERROR("Запрос учетных данных в СДБО 2.0 завершен с ошибками"),
    DBO2_CLIENT_SEARCH_FAIL("Запрос учетных данных в СДБО 2.0 не выполнен"),
    MPD_CLIENT_SEARCH("Запрос учетных данных в МПД"),
    MPD_CLIENT_SEARCH_DONE("Запрос учетных данных в МПД завершен"),
    MPD_CLIENT_SEARCH_DONE_WITH_ERRORS("Запрос учетных данных в МПД завершен с ошибками"),
    MPD_CLIENT_SEARCH_FAIL("Запрос учетных данных в МПД не выполнен");
    private final String description;

    @Override
    public String clientName() {
        return description;
    }
}
