/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { PartnerDetailComponent } from 'app/entities/partner/partner-detail.component';
import { Partner } from 'app/shared/model/partner.model';

describe('Component Tests', () => {
  describe('Partner Management Detail Component', () => {
    let comp: PartnerDetailComponent;
    let fixture: ComponentFixture<PartnerDetailComponent>;
    const route = ({ data: of({ partner: new Partner(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [PartnerDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(PartnerDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PartnerDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.partner).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
