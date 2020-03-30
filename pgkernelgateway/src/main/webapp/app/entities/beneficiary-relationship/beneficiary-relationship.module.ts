import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { PgkernelgatewaySharedModule } from 'app/shared';
import {
  BeneficiaryRelationshipComponent,
  BeneficiaryRelationshipDetailComponent,
  BeneficiaryRelationshipUpdateComponent,
  BeneficiaryRelationshipDeletePopupComponent,
  BeneficiaryRelationshipDeleteDialogComponent,
  beneficiaryRelationshipRoute,
  beneficiaryRelationshipPopupRoute
} from './';

const ENTITY_STATES = [...beneficiaryRelationshipRoute, ...beneficiaryRelationshipPopupRoute];

@NgModule({
  imports: [PgkernelgatewaySharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    BeneficiaryRelationshipComponent,
    BeneficiaryRelationshipDetailComponent,
    BeneficiaryRelationshipUpdateComponent,
    BeneficiaryRelationshipDeleteDialogComponent,
    BeneficiaryRelationshipDeletePopupComponent
  ],
  entryComponents: [
    BeneficiaryRelationshipComponent,
    BeneficiaryRelationshipUpdateComponent,
    BeneficiaryRelationshipDeleteDialogComponent,
    BeneficiaryRelationshipDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PgkernelgatewayBeneficiaryRelationshipModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
