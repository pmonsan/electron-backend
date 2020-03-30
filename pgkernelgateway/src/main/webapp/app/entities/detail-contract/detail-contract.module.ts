import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { PgkernelgatewaySharedModule } from 'app/shared';
import {
  DetailContractComponent,
  DetailContractDetailComponent,
  DetailContractUpdateComponent,
  DetailContractDeletePopupComponent,
  DetailContractDeleteDialogComponent,
  detailContractRoute,
  detailContractPopupRoute
} from './';

const ENTITY_STATES = [...detailContractRoute, ...detailContractPopupRoute];

@NgModule({
  imports: [PgkernelgatewaySharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    DetailContractComponent,
    DetailContractDetailComponent,
    DetailContractUpdateComponent,
    DetailContractDeleteDialogComponent,
    DetailContractDeletePopupComponent
  ],
  entryComponents: [
    DetailContractComponent,
    DetailContractUpdateComponent,
    DetailContractDeleteDialogComponent,
    DetailContractDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PgkernelgatewayDetailContractModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
