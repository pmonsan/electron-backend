import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { PgkernelgatewaySharedModule } from 'app/shared';
import {
  BeneficiaryComponent,
  BeneficiaryDetailComponent,
  BeneficiaryUpdateComponent,
  BeneficiaryDeletePopupComponent,
  BeneficiaryDeleteDialogComponent,
  beneficiaryRoute,
  beneficiaryPopupRoute
} from './';

const ENTITY_STATES = [...beneficiaryRoute, ...beneficiaryPopupRoute];

@NgModule({
  imports: [PgkernelgatewaySharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    BeneficiaryComponent,
    BeneficiaryDetailComponent,
    BeneficiaryUpdateComponent,
    BeneficiaryDeleteDialogComponent,
    BeneficiaryDeletePopupComponent
  ],
  entryComponents: [BeneficiaryComponent, BeneficiaryUpdateComponent, BeneficiaryDeleteDialogComponent, BeneficiaryDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PgkernelgatewayBeneficiaryModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
