import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { PgkernelgatewaySharedModule } from 'app/shared';
import {
  AccountStatusComponent,
  AccountStatusDetailComponent,
  AccountStatusUpdateComponent,
  AccountStatusDeletePopupComponent,
  AccountStatusDeleteDialogComponent,
  accountStatusRoute,
  accountStatusPopupRoute
} from './';

const ENTITY_STATES = [...accountStatusRoute, ...accountStatusPopupRoute];

@NgModule({
  imports: [PgkernelgatewaySharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    AccountStatusComponent,
    AccountStatusDetailComponent,
    AccountStatusUpdateComponent,
    AccountStatusDeleteDialogComponent,
    AccountStatusDeletePopupComponent
  ],
  entryComponents: [
    AccountStatusComponent,
    AccountStatusUpdateComponent,
    AccountStatusDeleteDialogComponent,
    AccountStatusDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PgkernelgatewayAccountStatusModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
