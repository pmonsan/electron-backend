import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { PgkernelgatewaySharedModule } from 'app/shared';
import {
  BeneficiaryTypeComponent,
  BeneficiaryTypeDetailComponent,
  BeneficiaryTypeUpdateComponent,
  BeneficiaryTypeDeletePopupComponent,
  BeneficiaryTypeDeleteDialogComponent,
  beneficiaryTypeRoute,
  beneficiaryTypePopupRoute
} from './';

const ENTITY_STATES = [...beneficiaryTypeRoute, ...beneficiaryTypePopupRoute];

@NgModule({
  imports: [PgkernelgatewaySharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    BeneficiaryTypeComponent,
    BeneficiaryTypeDetailComponent,
    BeneficiaryTypeUpdateComponent,
    BeneficiaryTypeDeleteDialogComponent,
    BeneficiaryTypeDeletePopupComponent
  ],
  entryComponents: [
    BeneficiaryTypeComponent,
    BeneficiaryTypeUpdateComponent,
    BeneficiaryTypeDeleteDialogComponent,
    BeneficiaryTypeDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PgkernelgatewayBeneficiaryTypeModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
