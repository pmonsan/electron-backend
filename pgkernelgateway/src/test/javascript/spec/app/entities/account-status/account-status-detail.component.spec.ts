/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { AccountStatusDetailComponent } from 'app/entities/account-status/account-status-detail.component';
import { AccountStatus } from 'app/shared/model/account-status.model';

describe('Component Tests', () => {
  describe('AccountStatus Management Detail Component', () => {
    let comp: AccountStatusDetailComponent;
    let fixture: ComponentFixture<AccountStatusDetailComponent>;
    const route = ({ data: of({ accountStatus: new AccountStatus(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [AccountStatusDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(AccountStatusDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AccountStatusDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.accountStatus).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
