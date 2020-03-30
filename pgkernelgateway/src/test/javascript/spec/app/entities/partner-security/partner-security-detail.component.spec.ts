/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { PartnerSecurityDetailComponent } from 'app/entities/partner-security/partner-security-detail.component';
import { PartnerSecurity } from 'app/shared/model/partner-security.model';

describe('Component Tests', () => {
  describe('PartnerSecurity Management Detail Component', () => {
    let comp: PartnerSecurityDetailComponent;
    let fixture: ComponentFixture<PartnerSecurityDetailComponent>;
    const route = ({ data: of({ partnerSecurity: new PartnerSecurity(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [PartnerSecurityDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(PartnerSecurityDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PartnerSecurityDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.partnerSecurity).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
