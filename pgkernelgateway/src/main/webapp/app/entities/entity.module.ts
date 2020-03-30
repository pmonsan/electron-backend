import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'pg-application',
        loadChildren: './pg-application/pg-application.module#PgkernelgatewayPgApplicationModule'
      },
      {
        path: 'pg-application-service',
        loadChildren: './pg-application-service/pg-application-service.module#PgkernelgatewayPgApplicationServiceModule'
      },
      {
        path: 'pg-channel-authorized',
        loadChildren: './pg-channel-authorized/pg-channel-authorized.module#PgkernelgatewayPgChannelAuthorizedModule'
      },
      {
        path: 'pg-channel',
        loadChildren: './pg-channel/pg-channel.module#PgkernelgatewayPgChannelModule'
      },
      {
        path: 'pg-data',
        loadChildren: './pg-data/pg-data.module#PgkernelgatewayPgDataModule'
      },
      {
        path: 'pg-message-model',
        loadChildren: './pg-message-model/pg-message-model.module#PgkernelgatewayPgMessageModelModule'
      },
      {
        path: 'pg-message-status',
        loadChildren: './pg-message-status/pg-message-status.module#PgkernelgatewayPgMessageStatusModule'
      },
      {
        path: 'pg-message',
        loadChildren: './pg-message/pg-message.module#PgkernelgatewayPgMessageModule'
      },
      {
        path: 'pg-detail-message',
        loadChildren: './pg-detail-message/pg-detail-message.module#PgkernelgatewayPgDetailMessageModule'
      },
      {
        path: 'pg-message-model-data',
        loadChildren: './pg-message-model-data/pg-message-model-data.module#PgkernelgatewayPgMessageModelDataModule'
      },
      {
        path: 'pg-response-code',
        loadChildren: './pg-response-code/pg-response-code.module#PgkernelgatewayPgResponseCodeModule'
      },
      {
        path: 'pg-transaction-type-1',
        loadChildren: './pg-transaction-type-1/pg-transaction-type-1.module#PgkernelgatewayPgTransactionType1Module'
      },
      {
        path: 'pg-transaction-type-2',
        loadChildren: './pg-transaction-type-2/pg-transaction-type-2.module#PgkernelgatewayPgTransactionType2Module'
      },
      {
        path: 'pg-account',
        loadChildren: './pg-account/pg-account.module#PgkernelgatewayPgAccountModule'
      },
      {
        path: 'contract',
        loadChildren: './contract/contract.module#PgkernelgatewayContractModule'
      },
      {
        path: 'detail-contract',
        loadChildren: './detail-contract/detail-contract.module#PgkernelgatewayDetailContractModule'
      },
      {
        path: 'contract-opposition',
        loadChildren: './contract-opposition/contract-opposition.module#PgkernelgatewayContractOppositionModule'
      },
      {
        path: 'account-balance',
        loadChildren: './account-balance/account-balance.module#PgkernelgatewayAccountBalanceModule'
      },
      {
        path: 'account-feature',
        loadChildren: './account-feature/account-feature.module#PgkernelgatewayAccountFeatureModule'
      },
      {
        path: 'customer',
        loadChildren: './customer/customer.module#PgkernelgatewayCustomerModule'
      },
      {
        path: 'customer-blacklist',
        loadChildren: './customer-blacklist/customer-blacklist.module#PgkernelgatewayCustomerBlacklistModule'
      },
      {
        path: 'person-document',
        loadChildren: './person-document/person-document.module#PgkernelgatewayPersonDocumentModule'
      },
      {
        path: 'meansofpayment',
        loadChildren: './meansofpayment/meansofpayment.module#PgkernelgatewayMeansofpaymentModule'
      },
      {
        path: 'beneficiary',
        loadChildren: './beneficiary/beneficiary.module#PgkernelgatewayBeneficiaryModule'
      },
      {
        path: 'customer-limit',
        loadChildren: './customer-limit/customer-limit.module#PgkernelgatewayCustomerLimitModule'
      },
      {
        path: 'partner-security',
        loadChildren: './partner-security/partner-security.module#PgkernelgatewayPartnerSecurityModule'
      },
      {
        path: 'partner',
        loadChildren: './partner/partner.module#PgkernelgatewayPartnerModule'
      },
      {
        path: 'service-integration',
        loadChildren: './service-integration/service-integration.module#PgkernelgatewayServiceIntegrationModule'
      },
      {
        path: 'customer-subscription',
        loadChildren: './customer-subscription/customer-subscription.module#PgkernelgatewayCustomerSubscriptionModule'
      },
      {
        path: 'periodicity',
        loadChildren: './periodicity/periodicity.module#PgkernelgatewayPeriodicityModule'
      },
      {
        path: 'limit-measure',
        loadChildren: './limit-measure/limit-measure.module#PgkernelgatewayLimitMeasureModule'
      },
      {
        path: 'limit-type',
        loadChildren: './limit-type/limit-type.module#PgkernelgatewayLimitTypeModule'
      },
      {
        path: 'price-plan',
        loadChildren: './price-plan/price-plan.module#PgkernelgatewayPricePlanModule'
      },
      {
        path: 'price',
        loadChildren: './price/price.module#PgkernelgatewayPriceModule'
      },
      {
        path: 'subscription-price',
        loadChildren: './subscription-price/subscription-price.module#PgkernelgatewaySubscriptionPriceModule'
      },
      {
        path: 'price-commission',
        loadChildren: './price-commission/price-commission.module#PgkernelgatewayPriceCommissionModule'
      },
      {
        path: 'pg-service',
        loadChildren: './pg-service/pg-service.module#PgkernelgatewayPgServiceModule'
      },
      {
        path: 'pg-module',
        loadChildren: './pg-module/pg-module.module#PgkernelgatewayPgModuleModule'
      },
      {
        path: 'connector-type',
        loadChildren: './connector-type/connector-type.module#PgkernelgatewayConnectorTypeModule'
      },
      {
        path: 'connector',
        loadChildren: './connector/connector.module#PgkernelgatewayConnectorModule'
      },
      {
        path: 'service-channel',
        loadChildren: './service-channel/service-channel.module#PgkernelgatewayServiceChannelModule'
      },
      {
        path: 'service-limit',
        loadChildren: './service-limit/service-limit.module#PgkernelgatewayServiceLimitModule'
      },
      {
        path: 'transaction',
        loadChildren: './transaction/transaction.module#PgkernelgatewayTransactionModule'
      },
      {
        path: 'detail-transaction',
        loadChildren: './detail-transaction/detail-transaction.module#PgkernelgatewayDetailTransactionModule'
      },
      {
        path: 'transaction-info',
        loadChildren: './transaction-info/transaction-info.module#PgkernelgatewayTransactionInfoModule'
      },
      {
        path: 'loan-instalment',
        loadChildren: './loan-instalment/loan-instalment.module#PgkernelgatewayLoanInstalmentModule'
      },
      {
        path: 'transaction-price',
        loadChildren: './transaction-price/transaction-price.module#PgkernelgatewayTransactionPriceModule'
      },
      {
        path: 'transaction-commission',
        loadChildren: './transaction-commission/transaction-commission.module#PgkernelgatewayTransactionCommissionModule'
      },
      {
        path: 'operation',
        loadChildren: './operation/operation.module#PgkernelgatewayOperationModule'
      },
      {
        path: 'internal-connector-request',
        loadChildren: './internal-connector-request/internal-connector-request.module#PgkernelgatewayInternalConnectorRequestModule'
      },
      {
        path: 'internal-connector-status',
        loadChildren: './internal-connector-status/internal-connector-status.module#PgkernelgatewayInternalConnectorStatusModule'
      },
      {
        path: 'activity-area',
        loadChildren: './activity-area/activity-area.module#PgkernelgatewayActivityAreaModule'
      },
      {
        path: 'account-type',
        loadChildren: './account-type/account-type.module#PgkernelgatewayAccountTypeModule'
      },
      {
        path: 'beneficiary-type',
        loadChildren: './beneficiary-type/beneficiary-type.module#PgkernelgatewayBeneficiaryTypeModule'
      },
      {
        path: 'beneficiary-relationship',
        loadChildren: './beneficiary-relationship/beneficiary-relationship.module#PgkernelgatewayBeneficiaryRelationshipModule'
      },
      {
        path: 'document-type',
        loadChildren: './document-type/document-type.module#PgkernelgatewayDocumentTypeModule'
      },
      {
        path: 'meansofpayment-type',
        loadChildren: './meansofpayment-type/meansofpayment-type.module#PgkernelgatewayMeansofpaymentTypeModule'
      },
      {
        path: 'customer-type',
        loadChildren: './customer-type/customer-type.module#PgkernelgatewayCustomerTypeModule'
      },
      {
        path: 'forex',
        loadChildren: './forex/forex.module#PgkernelgatewayForexModule'
      },
      {
        path: 'currency',
        loadChildren: './currency/currency.module#PgkernelgatewayCurrencyModule'
      },
      {
        path: 'feature',
        loadChildren: './feature/feature.module#PgkernelgatewayFeatureModule'
      },
      {
        path: 'operation-type',
        loadChildren: './operation-type/operation-type.module#PgkernelgatewayOperationTypeModule'
      },
      {
        path: 'partner-type',
        loadChildren: './partner-type/partner-type.module#PgkernelgatewayPartnerTypeModule'
      },
      {
        path: 'country',
        loadChildren: './country/country.module#PgkernelgatewayCountryModule'
      },
      {
        path: 'person-type',
        loadChildren: './person-type/person-type.module#PgkernelgatewayPersonTypeModule'
      },
      {
        path: 'service-authentication',
        loadChildren: './service-authentication/service-authentication.module#PgkernelgatewayServiceAuthenticationModule'
      },
      {
        path: 'account-status',
        loadChildren: './account-status/account-status.module#PgkernelgatewayAccountStatusModule'
      },
      {
        path: 'operation-status',
        loadChildren: './operation-status/operation-status.module#PgkernelgatewayOperationStatusModule'
      },
      {
        path: 'transaction-status',
        loadChildren: './transaction-status/transaction-status.module#PgkernelgatewayTransactionStatusModule'
      },
      {
        path: 'transaction-group',
        loadChildren: './transaction-group/transaction-group.module#PgkernelgatewayTransactionGroupModule'
      },
      {
        path: 'transaction-type',
        loadChildren: './transaction-type/transaction-type.module#PgkernelgatewayTransactionTypeModule'
      },
      {
        path: 'transaction-channel',
        loadChildren: './transaction-channel/transaction-channel.module#PgkernelgatewayTransactionChannelModule'
      },
      {
        path: 'pg-batch',
        loadChildren: './pg-batch/pg-batch.module#PgkernelgatewayPgBatchModule'
      },
      {
        path: 'transaction-property',
        loadChildren: './transaction-property/transaction-property.module#PgkernelgatewayTransactionPropertyModule'
      },
      {
        path: 'loan-instalment-status',
        loadChildren: './loan-instalment-status/loan-instalment-status.module#PgkernelgatewayLoanInstalmentStatusModule'
      },
      {
        path: 'person-gender',
        loadChildren: './person-gender/person-gender.module#PgkernelgatewayPersonGenderModule'
      },
      {
        path: 'question',
        loadChildren: './question/question.module#PgkernelgatewayQuestionModule'
      },
      {
        path: 'agent-type',
        loadChildren: './agent-type/agent-type.module#PgkernelgatewayAgentTypeModule'
      },
      {
        path: 'agent',
        loadChildren: './agent/agent.module#PgkernelgatewayAgentModule'
      },
      {
        path: 'pg-user',
        loadChildren: './pg-user/pg-user.module#PgkernelgatewayPgUserModule'
      },
      {
        path: 'user-profile',
        loadChildren: './user-profile/user-profile.module#PgkernelgatewayUserProfileModule'
      },
      {
        path: 'user-connection',
        loadChildren: './user-connection/user-connection.module#PgkernelgatewayUserConnectionModule'
      },
      {
        path: 'user-profile-data',
        loadChildren: './user-profile-data/user-profile-data.module#PgkernelgatewayUserProfileDataModule'
      },
      {
        path: 'pg-8583-request',
        loadChildren: './pg-8583-request/pg-8583-request.module#PgkernelgatewayPg8583RequestModule'
      },
      {
        path: 'pg-8583-status',
        loadChildren: './pg-8583-status/pg-8583-status.module#PgkernelgatewayPg8583StatusModule'
      },
      {
        path: 'pg-8583-callback',
        loadChildren: './pg-8583-callback/pg-8583-callback.module#PgkernelgatewayPg8583CallbackModule'
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ],
  declarations: [],
  entryComponents: [],
  providers: [],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PgkernelgatewayEntityModule {}
