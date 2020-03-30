import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { PgkernelgatewaySharedModule } from 'app/shared';
import {
  InternalConnectorRequestComponent,
  InternalConnectorRequestDetailComponent,
  InternalConnectorRequestUpdateComponent,
  InternalConnectorRequestDeletePopupComponent,
  InternalConnectorRequestDeleteDialogComponent,
  internalConnectorRequestRoute,
  internalConnectorRequestPopupRoute
} from './';

const ENTITY_STATES = [...internalConnectorRequestRoute, ...internalConnectorRequestPopupRoute];

@NgModule({
  imports: [PgkernelgatewaySharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    InternalConnectorRequestComponent,
    InternalConnectorRequestDetailComponent,
    InternalConnectorRequestUpdateComponent,
    InternalConnectorRequestDeleteDialogComponent,
    InternalConnectorRequestDeletePopupComponent
  ],
  entryComponents: [
    InternalConnectorRequestComponent,
    InternalConnectorRequestUpdateComponent,
    InternalConnectorRequestDeleteDialogComponent,
    InternalConnectorRequestDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PgkernelgatewayInternalConnectorRequestModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
