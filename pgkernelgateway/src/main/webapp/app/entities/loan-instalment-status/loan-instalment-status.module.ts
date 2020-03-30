import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { PgkernelgatewaySharedModule } from 'app/shared';
import {
  LoanInstalmentStatusComponent,
  LoanInstalmentStatusDetailComponent,
  LoanInstalmentStatusUpdateComponent,
  LoanInstalmentStatusDeletePopupComponent,
  LoanInstalmentStatusDeleteDialogComponent,
  loanInstalmentStatusRoute,
  loanInstalmentStatusPopupRoute
} from './';

const ENTITY_STATES = [...loanInstalmentStatusRoute, ...loanInstalmentStatusPopupRoute];

@NgModule({
  imports: [PgkernelgatewaySharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    LoanInstalmentStatusComponent,
    LoanInstalmentStatusDetailComponent,
    LoanInstalmentStatusUpdateComponent,
    LoanInstalmentStatusDeleteDialogComponent,
    LoanInstalmentStatusDeletePopupComponent
  ],
  entryComponents: [
    LoanInstalmentStatusComponent,
    LoanInstalmentStatusUpdateComponent,
    LoanInstalmentStatusDeleteDialogComponent,
    LoanInstalmentStatusDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PgkernelgatewayLoanInstalmentStatusModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
