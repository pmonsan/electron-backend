import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { PgkernelgatewaySharedModule } from 'app/shared';
import {
  AccountTypeComponent,
  AccountTypeDetailComponent,
  AccountTypeUpdateComponent,
  AccountTypeDeletePopupComponent,
  AccountTypeDeleteDialogComponent,
  accountTypeRoute,
  accountTypePopupRoute
} from './';

const ENTITY_STATES = [...accountTypeRoute, ...accountTypePopupRoute];

@NgModule({
  imports: [PgkernelgatewaySharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    AccountTypeComponent,
    AccountTypeDetailComponent,
    AccountTypeUpdateComponent,
    AccountTypeDeleteDialogComponent,
    AccountTypeDeletePopupComponent
  ],
  entryComponents: [AccountTypeComponent, AccountTypeUpdateComponent, AccountTypeDeleteDialogComponent, AccountTypeDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PgkernelgatewayAccountTypeModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
